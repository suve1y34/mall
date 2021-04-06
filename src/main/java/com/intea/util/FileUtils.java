package com.intea.util;

import com.intea.exception.AttachFileException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileUtils {

    /** 오늘 날짜 */
    private final String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));

    /** 업로드 경로 */
    private final String uploadPath = Paths.get("img", today).toString();

    /**
     * 서버에 생성할 파일명을 처리할 랜덤 문자열 반환
     * @return 랜덤 문자열
     */
    private final String getRandomString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /*
     * 서버에 첨부 파일을 생성하고, 업로드 파일 목록 반환
     * @param files    - 파일 Array
     * @param boardIdx - 게시글 번호
     * @return 업로드 파일 목록
     */
/*    public List<AttachDTO> uploadFiles(MultipartFile[] files, Long i_product) {

        List<AttachDTO> attachList = new ArrayList<>();

        File dir = new File(uploadPath);
        if (dir.exists() == false) {
            dir.mkdirs();
        }

        for (MultipartFile file : files) {
            if (file.getSize() < 1) {
                continue;
            }
            try {
                final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
                final String saveName = getRandomString() + "." + extension;
                final String thumb_saveName = "th_" + getRandomString() + "." + extension;
                final String img_url = uploadPath + saveName;
                final String thumb_img = uploadPath + thumb_saveName;

                File target = new File(uploadPath, saveName);
                file.transferTo(target);

                AttachDTO attach = new AttachDTO();
                attach.setI_product(i_product);
                attach.setThumb_img(thumb_img);
                attach.setImg_url(img_url);
                attach.setOriginal_name(file.getOriginalFilename());
                attach.setSave_name(saveName);
                attach.setSize(file.getSize());

                attachList.add(attach);

            } catch (IOException e) {
                throw new AttachFileException("[" + file.getOriginalFilename() + "] failed to save file...");

            } catch (Exception e) {
                throw new AttachFileException("[" + file.getOriginalFilename() + "] failed to save file...");
            }
        } // end of for

        return attachList;
    }*/

}