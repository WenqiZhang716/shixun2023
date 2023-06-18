<template>
    <div class="header">
        <el-menu mode="horizontal"
                 background-color="cornflowerblue"
                 text-color="#112f50"
                 class="menu"
                 @select="handleSelect">
            <el-menu-item index="1" ><h3>关于我们</h3></el-menu-item>
            <el-menu-item index="2"><h3>退出登录</h3></el-menu-item>
        </el-menu>

        <el-dialog
                title="提示"
                :visible.sync="dialogVisible"
                width="30%">
            <span>
                <h3 class="userTitle">难以想象，从入门到写出这么个前端来，我只花了四天……（四天，你知道我这四天是怎么过的吗嘤嘤嘤嘤嘤嘤嘤嘤）</h3>
            </span>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
  </span>
        </el-dialog>
    </div>

</template>

<script>
    export default {
        name: "HeaderContent",
        data() {
            return {
                dialogVisible: false,
                activeIndex: '1',
                activeIndex2: '1'
            };
        },
        methods: {
            openLoading(){
                const loading = this.$loading({         // 声明一个loading对象
                    lock: true,                           // 是否锁屏
                    fullScreen: true,                     //是否为全屏 Dialog
                    background: "rgba(255,255,255,0.7)"   //遮罩背景色
                });
                setTimeout(function () {                // 设定定时器，超时5S后自动关闭遮罩层，避免请求失败时，遮罩层一直存在的问题
                    loading.close();                      // 关闭遮罩层
                },5000);
                return loading;
            },
            test(){
                this.dialogVisible = true;
            },
            handleSelect(key, keyPath) {
                console.log(key);
                console.log(keyPath);
                if(key==='1'){
                    this.test();
                }else if(key==='2'){
                    const pageLoading = this.openLoading();
                    setTimeout(()=>{
                        localStorage.removeItem("token");
                        localStorage.removeItem("username");
                        localStorage.removeItem("id");
                        localStorage.removeItem("manifestId");
                    },1500);
                    pageLoading.close();
                    this.$message.success("退出登录！");
                    this.$router.push('/');
                }
            }
        }
    }
</script>

<style lang="less" scoped>
    .header{
        height: 50px;
        background: cornflowerblue;
    }
    .menu{
        display: flex;
        justify-content: flex-end;
    }

    .userTitle{
        text-align: center;
        align-items: center;
        display: flex;
        color:#112f50;
    }

</style>