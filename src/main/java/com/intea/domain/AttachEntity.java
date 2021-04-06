package com.intea.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachEntity extends CommonEntity {
    private Long i_file;
    private Long i_product;
    private String thumb_img;
    private String img_url;
    private String original_name;
    private String save_name;
    private Long size;
    private String delete_yn;
}
