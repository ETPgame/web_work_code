package Servlet;

import Service.UserService;
import Service.UserServiceImplements;
import entity.Page;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/paging")
public class PagingServlet extends HttpServlet {
    private UserService userService=new UserServiceImplements();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("turn to PagingServlet ok!");

        String pageInfo=req.getParameter("pageNow");// 当前页码
        if (pageInfo==null) pageInfo = "1";
        int pageNow=Integer.parseInt(pageInfo);// 当前页码
        int pageCount=10;// 一页的数量
        int totalRows= userService.getTotalRows();// 一页的记录数
        int totalPages=(totalRows%pageCount==0)?(totalRows/pageCount):(totalRows/pageCount+1);// 总页数

        if ( pageNow < 1){
            pageNow=1;
        }
        if (pageNow>totalPages){
            pageNow=totalPages;
        }
        Page page=new Page(pageNow,pageCount,totalPages,totalRows);
        List<User> users=userService.findAllUsersPageable(pageNow,pageCount);
        req.setAttribute("users",users);
        req.setAttribute("page",page);
        req.getRequestDispatcher("users.jsp").forward(req,resp);

    }
}
