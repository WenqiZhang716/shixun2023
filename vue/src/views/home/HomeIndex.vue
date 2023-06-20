<!--首页-->
<template>
        <div class="wrapper">
            <h2 class="userTitle">{{name}}，欢迎您使用本公司<h1>贼快的</h1>物流系统！</h2>
            <div >
            <el-card class="box-card" shadow="hover">
                <el-row :gutter="20">
                    <el-col :span="6">
                        <div>
                            <el-statistic group-separator="," :value="dataInfo.value1" :title="dataInfo.title1">
                                <template slot="suffix">
                               <span class="like">
                              <i class="el-icon-data-analysis" style="color:darkgrey" ></i>
                               </span>
                                </template>
                            </el-statistic>
                        </div>
                    </el-col>
                    <el-col :span="6">
                        <div>
                            <el-statistic :value="dataInfo.value2" :title="dataInfo.title2" >
                                <template slot="suffix">
                               <span class="like">
                              <i class="el-icon-s-finance" style="color:red" ></i>
                               </span>
                                </template>
                            </el-statistic>
                        </div>
                    </el-col>
                    <el-col :span="6">
                        <div>
                            <el-statistic group-separator="," :value="dataInfo.value3" :title="dataInfo.title3">
                                <template slot="suffix">
                               <span class="like">
                              <i class="el-icon-s-goods" style="color:cornflowerblue" ></i>
                               </span>
                                </template>
                            </el-statistic>
                        </div>
                    </el-col>
                    <el-col :span="6">
                        <div>
                            <el-statistic group-separator="," :value="dataInfo.value4" :title="dataInfo.title4">
                                <template slot="suffix">
                               <span class="like">
                              <i class="el-icon-circle-check" style="color:darkseagreen" ></i>
                               </span>
                                </template>
                            </el-statistic>
                        </div>
                    </el-col>
                </el-row>
            </el-card>
                <h3 class="userTitle">咳，目前只开辟了部分航线，所以有些地方不一定能送到....但快是肯定的…………</h3>
                <h4 class="userTitle">有用户中心，货单管理，账单管理等功能，请根据导航栏引导进行寄件操作(^ - ^)</h4>
            </div>
        </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: "HomeIndex",
        data() {
            return {
                name:"aa",
                dataInfo:{
                    value1:0,
                    value2:0,
                    value3:0,
                    value4:0,
                    title1:"总货单数",
                    title2:"待付款",
                    title4:"已完成",
                    title3:"进行中",
                }
            };
        },
        methods:{
            getData(){
                axios.post('/mani/manifest-get-home-data')
                    .then(res => {
                        console.log(res.data);
                        if (res.data.code === 0) {
                           this.dataInfo.value1=res.data.data.value1;
                            this.dataInfo.value2=res.data.data.value2;
                            this.dataInfo.value3=res.data.data.value3;
                            this.dataInfo.value4=res.data.data.value4;
                        } else {
                            let msg = res.data.message;
                            this.$message.error(msg);
                        }
                    })
                    .catch(error => {
                        console.error(error);
                    });

            }
        },
        mounted() {
           let username= localStorage.getItem('username');
            this.name=username;
            this.getData();
        }
    }
</script>

<style lang="less" scoped>
    .like {
        cursor: pointer;
        font-size: 25px;
        display: inline-block;
    }
    .wrapper{
        width: 100%;
        height: 100%;
        position: fixed;
        background: url("backHome.jpg");
        background-size: 100% 100%;

    }
    .box-card {
        width: 1000px;
        margin: 30px 0 0 30px;
        border: 1px solid #1f808c;

    }

    .userTitle{
        text-align: center;
        margin-top: 30px;
        margin-left: 200px;
        align-items: center;
        display: flex;
        color:#112f50;
    }
    .userTitle h1{
        margin-left: 10px;
        color:#1F808C;
    }


    .image-container {
        width: 1000px;
        margin: 30px 0 0 30px;
        background: url("back.jpg");
        /* 根据需要设置容器的样式，例如边框、背景等 */
    }



</style>