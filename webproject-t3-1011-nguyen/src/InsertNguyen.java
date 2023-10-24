
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertNguyen")
public class InsertNguyen extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InsertNguyen() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String teamName = request.getParameter("teamName");
      String player1 = request.getParameter("player1");
      String player2 = request.getParameter("player2");
      String player3 = request.getParameter("player3");
      String region = request.getParameter("region");

      Connection connection = null;
      String insertSql = " INSERT INTO MyRLTableNguyen (id, TEAMNAME, PLAYER1, PLAYER2, PLAYER3, REGION) values (default, ?, ?, ?, ?, ?)";

      try {
         DBConnectionNguyen.getDBConnection();
         connection = DBConnectionNguyen.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, teamName);
         preparedStmt.setString(2, player1);
         preparedStmt.setString(3, player2);
         preparedStmt.setString(4, player3);
         preparedStmt.setString(5, region);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Data to DB table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Team Name</b>: " + teamName + "\n" + //
            "  <li><b>Player 1</b>: " + player1 + "\n" + //
            "  <li><b>Player 2</b>: " + player2 + "\n" + //
            "  <li><b>Player 3</b>: " + player3 + "\n" + //
            "  <li><b>Region</b>: " + region + "\n" + //
            "</ul>\n");

      out.println("<a href=/webproject-t3-1011-nguyen/search_nguyen.html>Search Team</a> <br>");
      out.println("<a href=/webproject-t3-1011-nguyen/search_region_nguyen.html>Search Region</a> <br>");
      out.println("<a href=/webproject-t3-1011-nguyen/insert_nguyen.html>Insert Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
