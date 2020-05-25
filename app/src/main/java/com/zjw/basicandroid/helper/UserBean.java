package com.zjw.basicandroid.helper;

/**
 * Created by FrankZhang on 2017/11/14.
 */

public class UserBean {

    /**
     * seller_info : {"id":25,"unique_id":"5cb97a330d1f0","pid":24,"username":"scj_user","nickname":"手持机子账户","password":"c499b2882bfbee3237675ba36a30cb1e","salt":"cHwC9O","avatar":"/assets/img/avatar.png","email":"scj_user@qq.com","loginfailure":0,"logintime":1555928831,"expiration_time":0,"createtime":1555659411,"updatetime":1555928831,"token":"fada6be9-c6d0-4f3d-95ed-2ce09a017e9a","status":"normal","chain":0,"is_manager":0}
     * token : fada6be9-c6d0-4f3d-95ed-2ce09a017e9a
     */

    private SellerInfoBean seller_info;
    private String token;

    public SellerInfoBean getSeller_info() {
        return seller_info;
    }

    public void setSeller_info(SellerInfoBean seller_info) {
        this.seller_info = seller_info;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class SellerInfoBean {
        /**
         * id : 25
         * unique_id : 5cb97a330d1f0
         * pid : 24
         * username : scj_user
         * nickname : 手持机子账户
         * password : c499b2882bfbee3237675ba36a30cb1e
         * salt : cHwC9O
         * avatar : /assets/img/avatar.png
         * email : scj_user@qq.com
         * loginfailure : 0
         * logintime : 1555928831
         * expiration_time : 0
         * createtime : 1555659411
         * updatetime : 1555928831
         * token : fada6be9-c6d0-4f3d-95ed-2ce09a017e9a
         * status : normal
         * chain : 0
         * is_manager : 0
         */

        private int id;
        private String unique_id;
        private int pid;
        private String username;
        private String nickname;
        private String password;
        private String salt;
        private String avatar;
        private String email;
        private int loginfailure;
        private int logintime;
        private int expiration_time;
        private int createtime;
        private int updatetime;
        private String token;
        private String status;
        private int chain;
        private int is_manager;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUnique_id() {
            return unique_id;
        }

        public void setUnique_id(String unique_id) {
            this.unique_id = unique_id;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getLoginfailure() {
            return loginfailure;
        }

        public void setLoginfailure(int loginfailure) {
            this.loginfailure = loginfailure;
        }

        public int getLogintime() {
            return logintime;
        }

        public void setLogintime(int logintime) {
            this.logintime = logintime;
        }

        public int getExpiration_time() {
            return expiration_time;
        }

        public void setExpiration_time(int expiration_time) {
            this.expiration_time = expiration_time;
        }

        public int getCreatetime() {
            return createtime;
        }

        public void setCreatetime(int createtime) {
            this.createtime = createtime;
        }

        public int getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(int updatetime) {
            this.updatetime = updatetime;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getChain() {
            return chain;
        }

        public void setChain(int chain) {
            this.chain = chain;
        }

        public int getIs_manager() {
            return is_manager;
        }

        public void setIs_manager(int is_manager) {
            this.is_manager = is_manager;
        }
    }
}
