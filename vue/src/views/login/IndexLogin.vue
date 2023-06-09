<!--<template>-->
<!--&lt;!&ndash;    <h2>登录界面</h2>&ndash;&gt;-->
<!--    <div class="clearfix" id="login_wrap">-->
<!--        <div id="login">-->
<!--            <h2 class="title">登录页</h2>-->
<!--            <div class="login&#45;&#45;account">-->
<!--                <span>账号：</span>-->
<!--                <input type="text"-->
<!--                       placeholder="你的账号"-->
<!--                       name="account"-->
<!--                       v-model.trim="username"-->
<!--                />-->
<!--            </div>-->

<!--            <div class="login&#45;&#45;password">-->
<!--                <span>密码：</span>-->
<!--                <input type="password"-->
<!--                       placeholder="你的密码"-->
<!--                       name="password"-->
<!--                       v-model.trim="password"-->
<!--                />-->

<!--            </div>-->

<!--            <p class="login&#45;&#45;btn">-->
<!--                <el-button id="loginBtn" type="primary" @click="getLogin" >登陆</el-button>-->
<!--            </p>-->
<!--        </div>-->
<!--    </div>-->
<!--</template>-->

<!--<script>-->
<!--    export default {-->
<!--        name: "IndexLogin",-->
<!--    }-->
<!--</script>-->

<!--<style scoped lang="less">-->
<!--    .title{-->
<!--        text-align: center;-->
<!--        font-size: 22px;-->
<!--    }-->
<!--    #login_wrap{-->
<!--        position: relative;-->
<!--        -->
<!--    }-->

<!--</style>-->

<!--<template>-->
<!--    <div class="main-container">-->
<!--        <div class="login-container">-->
<!--            <el-form-->
<!--                    ref="loginForm"-->
<!--                    :model="loginForm"-->
<!--                    :rules="loginRules"-->
<!--                    class="login-form"-->
<!--            >-->
<!--                <h3 class="title">入侵检测系统</h3>-->
<!--                <body style="text-align: center">-->
<!--                <el-button type="text" disabled>用户登录</el-button>-->
<!--                <el-button type="text" @click="$router.replace('/manager')">管理员登录</el-button>-->
<!--                </body>-->
<!--                <el-form-item prop="username">-->
<!--                    <el-input-->
<!--                            v-model="loginForm.username"-->
<!--                            type="text"-->
<!--                            placeholder="用户名"-->
<!--                            prefix-icon="el-icon-user-solid"-->
<!--                    >-->
<!--                    </el-input>-->
<!--                </el-form-item>-->
<!--                <el-form-item prop="password">-->
<!--                    <el-input-->
<!--                            v-model="loginForm.password"-->
<!--                            type="password"-->
<!--                            prefix-icon="el-icon-s-help"-->
<!--                            placeholder="密码"-->
<!--                    >-->
<!--                    </el-input>-->
<!--                </el-form-item>-->
<!--&lt;!&ndash;                <el-checkbox v-model="loginForm.rememberMe" name="remember-me" style="margin-bottom: 25px">记住密码</el-checkbox>&ndash;&gt;-->
<!--                <el-button type="text" style="float:right" @click="$router.replace('/register')">注册</el-button>-->
<!--                <el-form-item style="width: 100%">-->
<!--                    <el-button-->
<!--                            size="medium"-->
<!--                            style="width: 100%;background-color: #5839ac;color: #ffffff"-->
<!--                            @click="handleLogin"-->
<!--                    >登录-->
<!--                    </el-button>-->
<!--                </el-form-item>-->
<!--            </el-form>-->
<!--        </div>-->
<!--        <div class="spaceMan-parent">-->
<!--            &lt;!&ndash;动画&ndash;&gt;-->
<!--        </div>-->
<!--        <lottie-player-->
<!--                src="https://assets4.lottiefiles.com/packages/lf20_zprb9vzj.json"-->
<!--                background="transparent"-->
<!--                speed="1"-->
<!--                class="bg"-->
<!--                loop-->
<!--                autoplay-->
<!--        >-->
<!--        </lottie-player>-->
<!--        <lottie-player-->
<!--                src="https://assets4.lottiefiles.com/packages/lf20_zprb9vzj.json"-->
<!--                background="transparent"-->
<!--                speed="1"-->
<!--                class="bg"-->
<!--                loop-->
<!--                autoplay-->
<!--        >-->
<!--        </lottie-player>-->
<!--    </div>-->
<!--</template>-->

<!--<script>-->
<!--    // import {messageInfo} from "@/utils/MessageInfo";-->
<!--    // import {Login} from "../../api/loginApi";-->


<!--    export default {-->
<!--        name: "IndexLogin",-->
<!--        data() {-->
<!--            return {-->
<!--                loginForm: {-->
<!--                    username: '',-->
<!--                    password: '',-->
<!--                    // rememberMe: false,-->
<!--                },-->
<!--                loginRules: {-->
<!--                    username: [-->
<!--                        {required: true, trigger: "blur", message: "请输入您的账号"},-->
<!--                    ],-->
<!--                    password: [-->
<!--                        {required: true, trigger: "blur", message: "请输入您的密码"},-->
<!--                    ],-->
<!--                },-->
<!--                loading: false,-->
<!--            };-->
<!--        },-->
<!--        // methods: {-->
<!--        //     handleLogin() {-->
<!--        //         this.$refs.loginForm.validate((valid) => {-->
<!--        //             if (!valid) return;-->
<!--        //             this.loading = true;-->
<!--        //             Login(this.loginForm).then((res) => {-->
<!--        //                 console.log(res);-->
<!--        //                 if (res) {//请求成功的相应值-->
<!--        //                     const {id} = res.result;-->
<!--        //                     const {name} = res.result;-->
<!--        //                     const {role} = res.result;-->
<!--        //                     const {privilege} = res.result;-->
<!--        //                     console.log(privilege)-->
<!--        //                     let r = role==0?'user':'manager';-->
<!--        //                     window.sessionStorage.setItem("token", id);-->
<!--        //                     window.sessionStorage.setItem("username", name);-->
<!--        //                     window.sessionStorage.setItem("role", r);-->
<!--        //                     window.sessionStorage.setItem("privilege",privilege);-->
<!--        //                     window.sessionStorage.setItem("userInfo",res.result);-->
<!--        //                     this.loading = false;-->
<!--        //                     this.$router.push("/home");-->
<!--        //                     messageInfo({message: "登陆成功！"});-->
<!--        //                 } else {-->
<!--        //                     this.loading = false;-->
<!--        //                 }-->
<!--        //             })-->
<!--        //         });-->
<!--        //     }-->
<!--        // },-->
<!--    };-->
<!--</script>-->

<!--<style scoped>-->
<!--    .main-container {-->
<!--        position: relative;-->
<!--        width: 100%;-->
<!--        height: 100%;-->
<!--    }-->

<!--    .login-container {-->
<!--        z-index: 1;-->
<!--        position: absolute;-->
<!--        left: 0;-->
<!--        height: 100%;-->
<!--        width: 30%;-->
<!--        background-color: #ffffff;-->
<!--        display: flex;-->
<!--        justify-content: center;-->
<!--        align-items: center;-->
<!--        border-radius: 6px;-->
<!--        box-shadow: 2px 3px 3px #888888;-->
<!--        opacity: 0.9;-->
<!--    }-->

<!--    .login-form {-->
<!--        z-index: 1;-->
<!--        width: 85%;-->
<!--    }-->

<!--    .title {-->
<!--        margin: 0 auto 30px auto;-->
<!--        text-align: center;-->
<!--        color: #707070;-->
<!--        font-size: 26px;-->
<!--    }-->

<!--    .spaceMan-parent {-->
<!--        position: absolute;-->
<!--        right: 0;-->
<!--        width: 70%;-->
<!--        height: 100%;-->
<!--    }-->

<!--    /*.spaceMan {*/-->
<!--    /*  z-index: 1;*/-->
<!--    /*  position: absolute;*/-->
<!--    /*  width: 500px;*/-->
<!--    /*  height: 500px;*/-->
<!--    /*  left: 0;*/-->
<!--    /*  top: 0;*/-->
<!--    /*  bottom: 10%;*/-->
<!--    /*  right: 30%;*/-->
<!--    /*  margin: auto auto;*/-->
<!--    /*  transform: translate(-50% ,-50%);*/-->
<!--    /*}*/-->

<!--    .bg {-->
<!--        z-index: -2;-->
<!--        width: 1550px;-->
<!--    }-->

<!--</style>-->


<template>
    <div class="login">
        <!-- 卡片 -->
        <el-card class="box-card">
            <h1 style="margin: 10px 0 14px 90px">北交带物流管理系统</h1>
            <!-- 登录 or 注册 -->
            <el-radio-group v-model="labelPosition" class="radioGroup" size="small">
                <el-radio-button label="login">登录</el-radio-button>
                <el-radio-button label="signIn">注册</el-radio-button>
            </el-radio-group>
            <!-- user输入表单 -->
            <el-form label-position="right" label-width="80px" :model="user">
                <el-form-item
                        label="用户名"
                        prop="name"
                        :rules="[ { required: true, message: '请输入用户名', trigger: 'blur' } ]">
                    <el-input v-model="user.name" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item
                        label="密码"
                        prop="password"
                        :rules="[ { required: true, message: '请输入密码', trigger: 'blur' } ]">
                    <el-input type="password" v-model="user.password" show-password></el-input>
                </el-form-item>
                <el-form-item
                        v-if="labelPosition==='signIn'"
                        label="确认密码"
                        prop="checkPassword"
                        :rules="[ { required: true, message: '请输入再次输入密码', trigger: 'blur' } ]">
                    <el-input type="password" v-model="user.checkPassword" show-password></el-input>
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
                }
            }
        },
        methods: {
            // 登录
            login() {
                this.$router.push("/home")

            },
            // 注册
            signIn() {
                if (this.user.checkPassword !== this.user.password) {
                    this.$message.error("两次输入的密码不一致!")
                }
            },
            // 重置表单
            resetForm() {
                this.user.name = ""
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
        margin: 120px 0 0 430px;
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