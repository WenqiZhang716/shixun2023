<template>
    <div>
        <el-card class="box-card" shadow="hover">
            <h1 style="margin: 10px 0 14px 90px">修改密码</h1>
            <!-- user输入表单 -->
            <el-form label-position="right" label-width="80px" :model="user">
                <el-form-item
                        label="原密码"
                        prop="oldPassword"
                        :rules="[ { required: true, message: '请输入原密码', trigger: 'blur' } ]">
                    <el-input type="password" v-model="user.oldPassword" show-password></el-input>
                </el-form-item>
                <el-form-item
                        label="新密码"
                        prop="password"
                        :rules="[ { required: true, message: '请输入密码', trigger: 'blur' } ]">
                    <el-input type="password" v-model="user.password" show-password></el-input>
                </el-form-item>

                <el-form-item
                        label="确认密码"
                        prop="checkPassword"
                        :rules="[ { required: true, message: '请输入再次输入密码', trigger: 'blur' } ]">
                    <el-input type="password" v-model="user.checkPassword" show-password></el-input>
                </el-form-item>
                <!--按钮-->
                <el-form-item class="button">
                    <el-button  type="warning" @click="changePass()"
                               :disabled="user.password===''||user.checkPassword===''" round>确认修改
                    </el-button>
                    <el-button @click="resetForm" round>重置</el-button>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: "PasswordChange",
        data() {
            return {
                // 用户数据
                user: {
                    password: '',
                    checkPassword: '',
                    oldPassword:''
                }
            }
        },
        methods: {
            // 修改密码
            changePass() {
                if (this.user.checkPassword !== this.user.password) {
                    this.$message.error("两次密码不一致！")
                }else if (this.user.oldPassword === this.user.password) {
                    this.$message.error("新密码不能与旧密码一样！")
                }else{
                    axios.post('/user/change-pw',{
                        "password":this.user.oldPassword,
                        "new-password":this.user.password
                    })
                        .then(res => {
                            console.log(res.data);
                            if (res.data.code===0){
                                this.$message.success('修改密码成功！');
                                this.$message.warning( '请重新登录！');
                                localStorage.removeItem('token');
                                localStorage.removeItem('username');
                                localStorage.removeItem('id');
                                this.$router.push("/")
                            }else{
                                let msg=res.data.message;
                                this.$message.error( msg);
                            }
                        })
                        .catch(error=>{
                            console.error(error);
                        });
                }

            },
            // 重置表单
            resetForm() {
                this.user.password = ""
                this.user.checkPassword = ""
            }
        }
    }
</script>

<style lang="less" scoped>

    .login{
        width: 100%;
        height: 100%;
        position: fixed;
        background-repeat: no-repeat;
        background-size: cover;
    }
    .box-card {
        width: 480px;
        margin: 20px 0 0 250px;
        border: 1px solid #1f808c;
    }
    .radioGroup{
        margin: 0 0 10px 150px;
    }
    .button{
        margin: 0 0 0 50px;
    }

    .el-form{
        .el-form-item{
            .el-input{
                width: 320px;
            }
        }
    }

</style>