package com.maple.userauth.endpoint;

import cn.hutool.core.thread.ThreadUtil;
import com.google.code.kaptcha.Producer;
import com.maple.common.core.constant.CommonConstants;
import com.maple.common.core.constant.SecurityConstants;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author zhua
 * 验证码生成逻辑处理类
 */
@Slf4j
@RestController
@AllArgsConstructor
public class ImageCodeController {
    private final Producer producer;
    private final RedisTemplate redisTemplate;

    @SneakyThrows
    @RequestMapping("/code")
    public void code(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //生成验证码
        String text = producer.createText();
        BufferedImage image = producer.createImage(text);

        //保存验证码信息
        String randomStr = request.getParameter("randomStr");
        ThreadUtil.execAsync(new Runnable() {
            @Override
            public void run() {
                redisTemplate.opsForValue().set(CommonConstants.DEFAULT_CODE_KEY + randomStr, text
                        , SecurityConstants.CODE_TIME, TimeUnit.SECONDS);
            }
        });

        // 转换流信息写出
        ServletOutputStream out = response.getOutputStream();
        try {
            ImageIO.write(image, "jpeg", out);
        } catch (IOException e) {
            log.error("ImageIO write err", e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }

    }
}