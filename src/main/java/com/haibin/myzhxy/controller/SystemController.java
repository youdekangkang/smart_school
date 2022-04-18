package com.haibin.myzhxy.controller;

import com.haibin.myzhxy.pojo.Admin;
import com.haibin.myzhxy.pojo.LoginForm;
import com.haibin.myzhxy.pojo.Student;
import com.haibin.myzhxy.pojo.Teacher;
import com.haibin.myzhxy.service.AdminService;
import com.haibin.myzhxy.service.StudentService;
import com.haibin.myzhxy.service.TeacherService;
import com.haibin.myzhxy.util.CreateVerifiCodeImage;
import com.haibin.myzhxy.util.JwtHelper;
import com.haibin.myzhxy.util.Result;
import io.jsonwebtoken.JwtParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

//防止非增删改查操作的controller
@RestController
@RequestMapping("/sms/system")
public class SystemController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm, HttpServletRequest request){
        //验证码校验
        HttpSession session = request.getSession();
        String sessionVerifiCode = (String) session.getAttribute("verifiCode");
        String loginVerfiCode = loginForm.getVerifiCode();
        if("".equals(sessionVerifiCode) || null == sessionVerifiCode){
            return Result.fail().message("验证码失效，请刷新后重试");
        }
        if(!sessionVerifiCode.equalsIgnoreCase(loginVerfiCode)){
            return Result.fail().message("验证码错误，请重试");
        }
        //若成功 则需要从session中移除现有验证码
        session.removeAttribute("verifiCode");

        //分用户类型进行校验
        //准备一个map用于存放相应的数据
        Map<String,Object> map = new LinkedHashMap<>();
        switch (loginForm.getUserType()){
            case 1:
                try {
                    Admin admin = adminService.login(loginForm);
                    if( null != admin ){
                        //用户的id转换为密文，以token的名称向客户端反馈
                        String token = JwtHelper.createToken(admin.getId().longValue(),1);
                        map.put("token", token);
                    }else{
                        //作为异常信息丢给catch
                        throw new RuntimeException("用户名或者密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 2:
                try {
                    Student student = studentService.login(loginForm);
                    if( null != student ){
                        //用户的id转换为密文，以token的名称向客户端反馈
                        String token = JwtHelper.createToken(student.getId().longValue(),2);
                        map.put("token", token);
                    }else{
                        //作为异常信息丢给catch
                        throw new RuntimeException("用户名或者密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 3:
                try {
                    Teacher teacher = teacherService.login(loginForm);
                    if( null != teacher ){
                        //用户的id转换为密文，以token的名称向客户端反馈
                        String token = JwtHelper.createToken(teacher.getId().longValue(),3);
                        map.put("token", token);
                    }else{
                        //作为异常信息丢给catch
                        throw new RuntimeException("用户名或者密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
        }
        return Result.fail().message("找不到用户，请重试");
    }


    @RequestMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response){
        //获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取图片上的验证码
        String verifiCode = new String( CreateVerifiCodeImage.getVerifiCode() );
        //将验证码放入session，为下一次验证做准备
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode",verifiCode);
        //将验证码图片相应给浏览器
        try {
            ImageIO.write(verifiCodeImage,"JPEG",response.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
