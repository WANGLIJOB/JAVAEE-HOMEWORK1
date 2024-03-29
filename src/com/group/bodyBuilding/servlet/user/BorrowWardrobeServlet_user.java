package com.group.bodyBuilding.servlet.user;

import com.group.bodyBuilding.dao.WardrobeDao;
import com.group.bodyBuilding.factory.Factory;
import com.group.bodyBuilding.vo.Wardrobe;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.group.bodyBuilding.ReadCookie.ReadCookieMap;

public class BorrowWardrobeServlet_user extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = null;
        Map<String, Cookie> cookieMap = ReadCookieMap(req);
        if (cookieMap.containsKey("cookie_id")) {
            //System.out.println("cookie存在");
            Cookie cookie = (Cookie) cookieMap.get("cookie_id");
            id = cookie.getValue();
            /*System.out.println(id);*/
        }
        String wardrobeId = req.getParameter("wardrobeId");
        /*boolean wardrobeState = (boolean) req.getParameter("wardrobeState");*/
        String wardrobeState = req.getParameter("wardrobeState");
        System.out.println(wardrobeState);
        System.out.println(wardrobeId);
        //归还
        WardrobeDao wardrobeDao = Factory.getWardrobeDao();
        if(wardrobeState.equals("false")){
            boolean state = wardrobeDao.changeState("0",wardrobeId);
            List<Wardrobe> wardrobeList = new ArrayList<>();
            wardrobeList = wardrobeDao.findAllWardrobe();
            req.setAttribute("wardrobeList", wardrobeList);
            req.setAttribute("state", state);
            req.setAttribute("state2", true);
            req.setAttribute("state1", false);
            req.getRequestDispatcher("/user/borrowWardrobe.jsp").forward(req, resp);
        }
        //借用
        else {
            boolean state = wardrobeDao.changeState(id,wardrobeId);
            req.setAttribute("state2",false);
            req.setAttribute("state1",true);
            req.setAttribute("state",state);
            req.getRequestDispatcher("/user/borrowWardrobe.jsp").forward(req,resp);

        }

    }
}
