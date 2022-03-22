package shop.daijian.support.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.daijian.common.exception.BizException;
import shop.daijian.common.support.BaseController;
import shop.daijian.common.wrapper.ResultWrapper;
import shop.daijian.support.config.AliyunOssProperties;
import shop.daijian.support.enums.FileTypeEnum;
import shop.daijian.support.enums.UploadStatusEnum;
import shop.daijian.support.util.FileUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

/**
 * @author liuxin
 * @date 2019/8/12 21:39
 */
@Slf4j
@Api(tags = "上传接口")
@RestController
@RequestMapping("/upload")
public class UploadController extends BaseController {

    @Autowired
    private AliyunOssProperties aliyunOssProperties;

    @Autowired
    private OSSClient ossClient;

    /**
     * 最大上传文件大小, 8MB, 单位byte
     */
    private static final long FILE_MAX_SIZE = 3 * 1024 * 1024;

    @ApiOperation("上传文件")
    @PostMapping("/file/{fileTypeEnum}")
    public ResultWrapper<Map<String, String>> uploadFile(@RequestHeader String userId, MultipartFile file, @ApiParam("上传文件类型") @PathVariable FileTypeEnum fileTypeEnum) throws IOException {
        //文件是否为空
        if (file == null) {
            throw new BizException(UploadStatusEnum.FILE_CAN_NOT_NULL);
        }
        //文件大小是否大于最大上传文件大小
        if (file.getSize() > FILE_MAX_SIZE) {
            throw new BizException(UploadStatusEnum.OVERSIZE_FILE);
        }
        //得到本地文件名称
        String originalFilename = file.getOriginalFilename();
        //得到文件名后缀
        String suffix = originalFilename.substring(Objects.requireNonNull(originalFilename).lastIndexOf("."));
        //文件名后缀不符合标准则抛出异常
        if (!FileUtil.isSuffixLegal(suffix)) {
            throw new BizException(UploadStatusEnum.ILLEGAL_SUFFIX);
        }
        //获得上传文件名称
        String fileName = buildFileName(fileTypeEnum, suffix);
        //创建数据元对象
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType(file.getContentType());
        meta.setContentDisposition("inline;filename=\"" + fileName + "\"");
        //创建OSS客户端对象
        try (InputStream inputStream = file.getInputStream()) {
            ossClient.putObject(aliyunOssProperties.getBucket(), fileName, inputStream, meta);
        } catch (Exception e) {
            throw new BizException(UploadStatusEnum.FAIL_READ_FILE);
        }
        if (fileTypeEnum.equals(FileTypeEnum.AVATAR)) {
            return ResultWrapper.success("url", aliyunOssProperties.getRootUrl() + fileName + "?x-oss-process=style/avatar");
        }
        return ResultWrapper.success("url", aliyunOssProperties.getRootUrl() + fileName);
    }

    private String buildFileName(FileTypeEnum fileTypeEnum, String suffix) {
        return aliyunOssProperties.getBaseDir() + fileTypeEnum.getDir().toLowerCase() + IdWorker.getIdStr() + suffix;
    }

}


