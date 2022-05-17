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
import com.haibin.myzhxy.util.ResultCodeEnum;
import io.jsonwebtoken.JwtParser;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

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

    @ApiOperation("文件上传统一入口")
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(
            @ApiParam("图片文件")  @RequestPart("multipartFile") MultipartFile multipartFile
    ){
        // 使用UUID生成新的文件名
        String uuid = UUID.randomUUID().toString().replace("-","").toLowerCase();
        // 生成新的文件名
        String filename = uuid.concat(multipartFile.getOriginalFilename());
        // 生成文件保存路径(因为实际生产中会使用真正的文件存储服务器)
        String portraitPath ="D:/代码库/InfoSys/smart_school/target/classes/static/images".concat(filename);
        //保存文件
        try {
            multipartFile.transferTo(new File(portraitPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String headerImg ="upload/"+filename;
        return Result.ok(headerImg);

    }


    @GetMapping("/getInfo")
    public Result getInfoByToken(@RequestHeader("token") String token){
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        //从token中解析出用户id以及用户的类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        Map<String,Object> map = new LinkedHashMap<>();
        switch (userType) {
            case 1:
                Admin admin = adminService.getAdminById(userId);
                map.put("userType",1);
                map.put("userId",admin);
                break;
            case 2:
                Student student = studentService.getStudentById(userId);
                map.put("userType",2);
                map.put("userId",student);
                break;
            case 3:
                Teacher teacher = teacherService.getTeacherById(userId);
                map.put("userType",3);
                map.put("userId",teacher);
                break;
        }

        return Result.ok(map);
    }



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
