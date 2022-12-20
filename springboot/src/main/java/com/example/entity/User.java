package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author danny
 * @since 2022-12-20
 */
@TableName("sys_user")
@ApiModel(value = "User对象", description = "")
@ToString
@Getter
@Setter
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("ID")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("用戶名")
      private String username;

      @ApiModelProperty("密碼")
      private String password;

      @ApiModelProperty("暱稱")
      private String nickname;

      @ApiModelProperty("電子信箱")
      private String email;

      @ApiModelProperty("電話")
      private String phone;

      @ApiModelProperty("地址")
      private String address;

      @ApiModelProperty("建立時間")
      private LocalDateTime createTime;

      @ApiModelProperty("假刪除  0是存在 1為刪除")
      private String isDelete;

      @ApiModelProperty("頭像")
      private String avatarUrl;

    
    public Integer getId() {
        return id;
    }

      public void setId(Integer id) {
          this.id = id;
      }
    
    public String getUsername() {
        return username;
    }

      public void setUsername(String username) {
          this.username = username;
      }
    
    public String getPassword() {
        return password;
    }

      public void setPassword(String password) {
          this.password = password;
      }
    
    public String getNickname() {
        return nickname;
    }

      public void setNickname(String nickname) {
          this.nickname = nickname;
      }
    
    public String getEmail() {
        return email;
    }

      public void setEmail(String email) {
          this.email = email;
      }
    
    public String getPhone() {
        return phone;
    }

      public void setPhone(String phone) {
          this.phone = phone;
      }
    
    public String getAddress() {
        return address;
    }

      public void setAddress(String address) {
          this.address = address;
      }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }

      public void setCreateTime(LocalDateTime createTime) {
          this.createTime = createTime;
      }
    
    public String getIsDelete() {
        return isDelete;
    }

      public void setIsDelete(String isDelete) {
          this.isDelete = isDelete;
      }
    
    public String getAvatarUrl() {
        return avatarUrl;
    }

      public void setAvatarUrl(String avatarUrl) {
          this.avatarUrl = avatarUrl;
      }

    @Override
    public String toString() {
        return "User{" +
              "id=" + id +
                  ", username=" + username +
                  ", password=" + password +
                  ", nickname=" + nickname +
                  ", email=" + email +
                  ", phone=" + phone +
                  ", address=" + address +
                  ", createTime=" + createTime +
                  ", isDelete=" + isDelete +
                  ", avatarUrl=" + avatarUrl +
              "}";
    }
}
