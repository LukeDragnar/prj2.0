<%-- 
    Document   : search
    Created on : Jun 5, 2023, 5:11:18 PM
    Author     : ASUS
--%>

<%@page import="nghiatd.user.RegistrationDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
<!DOCTYPE html>
<style>
    body {
        background-image: url('https://scontent.fsgn5-15.fna.fbcdn.net/v/t1.15752-9/290939005_1499751347133205_7402238368432585483_n.png?_nc_cat=111&ccb=1-7&_nc_sid=ae9488&_nc_ohc=Pyosx4mXqXUAX8sabZ3&_nc_ht=scontent.fsgn5-15.fna&oh=03_AdSMoUq9p0acDmoPbX7WgD1Z65sXt5wjKRylIhV-NvjxsQ&oe=6531E4AB'); /* Đường dẫn đến hình nền */
        background-size: cover; /* Đảm bảo hình nền nằm vừa với kích thước của trình duyệt */
        background-repeat: no-repeat; /* Ngăn lặp lại hình nền */
        background-attachment: fixed; /* Giữ hình nền cố định khi cuộn trang */
        }
    .google-logo {
            max-width: 800px; 
            height: auto; /* Maintain aspect ratio */
            vertical-align: middle;
            margin-bottom: 10px;
        }
    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
    }

    table, th, td {
        border: 1px solid #ddd;
    }

    th, td {
        padding: 8px;
        text-align: left;
    }

    th {
        background-color: #f2f2f2;
    }

    tr:nth-child(even) {
        background-color: #f2f2f2;
    }

    tr:hover {
        background-color: #ddd;
    }

    h2 {
        font-size: 18px;
        color: #666;
        margin-top: 20px;
    }
    .background-container {
            background-color: rgba(255, 255, 255, 0.9); /* Adjust the opacity (0.9) as needed */
        }
</style>
<html>
    <html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Search</title>
</head>
<body style="font-family: 'Roboto', sans-serif; text-align: center; background-color: #f2f2f2;">
<% if(session.getAttribute("txtUsername") != null) {   %>
<div style="background-color:rgba(255, 255, 255, 0.7); padding: 20px; border: 1px solid #ddd; border-radius: 8px; margin: 20px auto; width: 800px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);">
    <h3 style="color: red">
        Welcome, <%= session.getAttribute("txtUsername")%>  <%=session.getAttribute("currentURL")%>
    </h3>
    <div>
        <img src="https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/449d2971-6fc8-4f68-b507-1e2bf3345d11/d8vpwh7-d1307bdb-24cb-4d70-9295-1dc479efdd29.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzQ0OWQyOTcxLTZmYzgtNGY2OC1iNTA3LTFlMmJmMzM0NWQxMVwvZDh2cHdoNy1kMTMwN2JkYi0yNGNiLTRkNzAtOTI5NS0xZGM0NzllZmRkMjkucG5nIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.G9kclk1D3hgk5Fw5bp_DZ2OosKCgp_PwebyWim9EfTU" alt="Google Logo" class="google-logo">
    </div>
    
    <form action="MainController" style="text-align: center;">
        <input type="text" name="txtSearchValue" value="" placeholder="Search for a user" style="width: 80%; padding: 10px; font-size: 16px; border: 1px solid #ddd; border-radius: 4px; margin-bottom: 10px;">
        <br>
        <input type="submit" value="Search" name="btAction" style="background-color: #4285f4; color: #fff; border: none; padding: 10px 20px; font-size: 16px; border-radius: 4px; cursor: pointer;">                           
        <input type="submit" value="Logout" name="btAction"/>                              
    </form>
    <br>
        <%
            String searchValue = request.getParameter("txtSearchValue");
            if(searchValue != null){
                List<RegistrationDTO> result = (List<RegistrationDTO>)
                        request.getAttribute("SEARCH_RESULT");
                if (result != null){//khi tìm được
                    %>
                    <table border ="1">
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>UserID</th>
                                <th>Password</th> 
                                <th>FullName</th>
                                <th>roleID</th>
                                <th>Update</th>
                                <th>Delete</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                int count = 0;
                                for(RegistrationDTO dto : result){
                                        String urlRewriting = "MainController" + "?btAction=delete" + "&username=" + dto.getUserID() + "&lastSearchValue=" + searchValue;
                                    %>
                                <tr>
                        <form action="MainController" method="POST">
                                <td>
                                    <%= ++count %>
                                  .</td>
                                <td>
                                    <%= dto.getUserID()%>
                                            <input type="hidden" name="txtUsername" 
                                                   value="<%= dto.getUserID()%>" />                                  
                                </td>
                                <td>
                                    <input type="text" name="txtPassword" 
                                                   value="<%= dto.getPassword() %>" />
                                </td> 
                                <td>
                                    <input type="text" name="txtFullname" 
                                                   value="<%= dto.getFullName()%>" />                                   
                                </td>
                                <td>
                                    <%= dto.getRoleID()%>
                                </td>
                                <td>                               
                                            <input type="submit" value="Update" name="btAction"/>
                                            <input type="hidden" name="lastSearchValue" 
                                                   value="<%= searchValue %>" />
                                </td>
                                <td>
                                    <a href="<%= urlRewriting %>">Delete</a>
                                </td>
                            </tr>
                        </form>    
                            <%
                                }//end
                            %>
                        </tbody>
                    </table>
            <%
                }else{//search ko có
                    //tách code vì phần này phát sinh code html
                    //sctiptlet đc gọi là fragment code trộn hỗn hợp java và html
                    %>
                    <h2/>
                        No record is matched
                    <h2/>
            <%
                }
            }//end prevent access directly resource of first access
        %>
</div>
              <%   } %>   
           
       
              
                    </body>
</html>
