import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchRegionNguyen")
public class SearchRegionNguyen extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SearchRegionNguyen() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnectionNguyen.getDBConnection();
         connection = DBConnectionNguyen.connection;

         if (keyword.isEmpty()) {
            String selectSQL = "SELECT * FROM MyRLTableNguyen";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
            String selectSQL = "SELECT * FROM MyRLTableNguyen WHERE REGION LIKE ?";
            String theTeam = "%" + keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theTeam);
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            int id = rs.getInt("id");
            String teamName = rs.getString("teamName").trim();
            String player1 = rs.getString("player1").trim();
            String player2 = rs.getString("player2").trim();
            String player3 = rs.getString("player3").trim();
            String region = rs.getString("region").trim();

            if (keyword.isEmpty() || region.contains(keyword)) {
               out.println("ID: " + id + ", ");
               out.println("Team Name: " + teamName + ", ");
               out.println("Player 1: " + player1 + ", ");
               out.println("Player 2: " + player2 + ", ");
               out.println("Player 3: " + player3 + ", ");
               out.println("Region: " + region + "<br>");
            }
         }
         out.println("<a href=/webproject-t3-1011-nguyen/search_nguyen.html>Search Team</a> <br>");
         out.println("<a href=/webproject-t3-1011-nguyen/search_region_nguyen.html>Search Region</a> <br>");
         out.println("<a href=/webproject-t3-1011-nguyen/insert_nguyen.html>Insert Data</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
