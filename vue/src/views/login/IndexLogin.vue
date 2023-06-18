
<template>
    <div class="wrapper">
        <!-- 卡片 -->
        <el-card class="box-card" shadow="hover">
            <h1 style="margin: 10px 0 14px 90px">贼快的物流管理系统</h1>
            <!-- 登录 or 注册 -->
            <el-radio-group v-model="labelPosition" class="radioGroup" size="small">
                <el-radio-button label="login">登录</el-radio-button>
                <el-radio-button label="signIn">注册</el-radio-button>
            </el-radio-group>
            <!-- user输入表单 -->
            <el-form label-position="right" label-width="80px" :model="user">
                <el-form-item
                        label="手机号"
                        prop="name"
                        :rules="[ { required: true, message: '请输入手机号码', trigger: 'blur' } ]">
                    <el-input v-model="user.name" maxlength="11" id="username"></el-input>
                </el-form-item>
                <el-form-item
                        label="密码"
                        prop="password"
                        :rules="[ { required: true, message: '请输入密码', trigger: 'blur' } ]">
                    <el-input type="password" v-model="user.password" id="password" show-password></el-input>
                </el-form-item>
                <el-form-item
                        v-if="labelPosition==='login'">
<!--                    <el-switch-->
<!--                            style="display: block"-->
<!--                            v-model="user.type"-->
<!--                            active-color="#ff4949"-->
<!--                            inactive-color="#13ce66"-->
<!--                            active-text="承运人"-->
<!--                            inactive-text="托运人"-->
<!--                            id="type"-->
<!--                            class="switchClass">-->
<!--                    </el-switch>-->
                </el-form-item>
                <el-form-item
                        v-if="labelPosition==='signIn'"
                        label="确认密码"
                        prop="checkPassword"
                        :rules="[ { required: true, message: '请输入再次输入密码', trigger: 'blur' } ]">
                    <el-input type="password" v-model="user.checkPassword" id="checkPassword" show-password></el-input>
                </el-form-item>
                <!--按钮-->
                <el-form-item class="button">
                    <el-button v-if="labelPosition==='login'" type="warning" @click="login"
                               :disabled="user.name===''||user.password===''" round>登录
                    </el-button>
                    <el-button v-if="labelPosition==='signIn'" type="warning" @click="signIn"
                               :disabled="user.name===''||user.password===''||user.checkPassword===''" round>注册
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
        name:"IndexLogin",
        data() {
            return {
                labelPosition: 'login',  // 开始先定位到登录
                // 用户数据
                user: {
                    name: '',
                    password: '',
                    checkPassword: '',
                    type:false //代表用户类型，默认是用户
                },
            }
        },
        methods: {
            // 登录
            login() {
               const username=document.getElementById('username').value;
               const password=document.getElementById('password').value;
                let typ=0;
                axios.post('/auth/login',{
                    username:username,
                    password:password,
                    type:typ
                })
                    .then(res => {
                        console.log(res.data);
                        let datas=res.data.data;
                        if (res.data.code===0){
                            let token=datas.accessToken;
                            let username=datas.username;
                            localStorage.setItem('token',token);
                            localStorage.setItem('username',username);
                            localStorage.setItem('id',datas.id);
                            this.$message.success('登陆成功！');
                            this.$router.push("/home")
                        }else{

                            let msg=res.data.message;
                            // this.$notify({
                            //     title: '登陆失败！',
                            //     message: msg,
                            //     type: 'warning',
                            //     position: 'top-left'
                            // });
                            this.$message.error( msg);
                        }
                    })
                .catch(error=>{
                    console.error(error);
                });
            },
            // 注册
            signIn() {
                if (this.user.checkPassword !== this.user.password) {
                    this.$message.error("两次输入的密码不一致!")
                }
                else if (/^(13[0-9]|14[0-9]|15[0-9]|16[6]|18[0-9]|19[6,9]|17[0-9])\d{8}$/i.test(this.user.name) === false) {
                    this.$message.error("请输入正确手机号!")
                }else if (this.user.password.length<6){
                    this.$message.error("密码不能小于六位!")
                }else{
                    const username=document.getElementById('username').value;
                    const password=document.getElementById('password').value;
                    axios.post('/auth/signup',{
                        username:username,
                        password:password,
                    })
                        .then(res => {
                            console.log(res.data);
                            if (res.data.code===0){
                                this.$message.success('注册成功！');
                                this.$router.go(0);
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
                this.user.name = ""
                this.user.password = ""
                this.user.checkPassword = ""
            },

        }
    }
</script>

<style lang="less" scoped>
    .wrapper{
        width: 100%;
        height: 100%;
        position: fixed;
        background: url("login2.jpg");
        background-size: 100% 100%;
    }
    .box-card {
        width: 480px;
        margin: 120px 0 0 430px;
        border: 3px solid #1f808c;
        opacity: 0.83;
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
    .switchClass{
        margin: 0 0 10px 50px;
    }

</style>