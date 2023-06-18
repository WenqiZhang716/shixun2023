<template>
    <div>
            <el-card class="box-card" shadow="hover">

                    <el-row >
                        <el-col :span="3">
                            <el-button
                                    size="mini"
                                    type="primary"
                                    @click="returnLast" round>返回</el-button>
                        </el-col>
                        <el-col :span="3">
                            <el-button
                                    size="mini"
                                    @click="reflushIt" round>刷新</el-button>
                        </el-col>
                    </el-row>

                <el-main>
                    <h1 class="userTitle">运输路径及状态</h1>
                    <h5 class="userTitle">请刷新查看最新运输进度</h5>
                <el-steps :active="activeStep" direction="vertical" align-center class="steps">
                    <el-step v-for="(step, index) in steps" :key="index" :title="step.title" :description="step.desc">
                        <div slot="description" v-html="formatDescription(step.desc)"></div>
                    </el-step>
                </el-steps>
                </el-main>
            </el-card>
    </div>
</template>

<script>
    import axios from "axios";
    export default {
        name: "transportManifest",
        mounted() {
            this.manifestId=localStorage.getItem("manifestId");

            axios.post('/transport/get-step-info-list', {
                manifest_id:this.manifestId
            })
                .then(res => {
                    console.log(res.data);
                    if (res.data.code === 0) {
                        this.steps=res.data.data.path;
                        this.activeStep=res.data.data.status;
                    } else {
                        let msg = res.data.message;
                        this.$message.error(msg);
                    }
                })
                .catch(error => {
                    console.error(error);
                });

        },
        data() {
            return {
                manifestId:0,
                activeStep: 2, // 当前激活的步骤索引
                steps: [
                    {title:"step 1",
                        desc:"\naaaaaaaaaaaaa\nnaaaaaaa\nnaaa",
                    },
                    {title:"step2",
                        desc:"aaaa\naaaaaaaaa\nnaaaaaaanaaa"
                    },
                    {
                        title:"step3",
                        desc:"aaaaaaaaaaaaa\nnaaaaaaanaaa"
                    }
                ], // 步骤数据
            };
        },
        methods: {
            returnLast(){
                this.$router.go(-1);
            },
            reflushIt(){
                this.$router.go(0);
            },
            formatDescription(description) {
                return description.replace(/\n/g, '<br>');
            },
            handleChange(val) {
                console.log(val);
            }
        }
    }
</script>

<style lang="less" scoped>
    .box-card {
        width: 580px;
        margin: 10px 0 0 250px;
        border: 1px solid #1f808c;

    }
    .userTitle{
        text-align: center;
        color:#112f50;
    }
    .steps{
        height: 500px;
        margin-top: 30px;
    }

</style>