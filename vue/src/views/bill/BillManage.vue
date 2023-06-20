<template>
    <div>
        <el-card class="box-card" shadow="hover">
            <h1 class="userTitle">账单管理</h1>
            <el-container id="content">
                <el-header>
                    <div label="筛选条件: " prop="condition" class="search">
                        <el-select v-model="condition" placeholder="请选择" class="inputSize">
                            <el-option
                                    v-for="item in options3"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                                    :disabled="item.disabled">
                            </el-option>
                        </el-select>
                        <el-button
                                size="mini"
                                type="primary"
                                @click="findByType" round>搜索</el-button>
                    </div>
                </el-header>
                <el-main>
                    <el-table
                            :data="tableData.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize)"
                            style="width: 100%"
                            stripe
                            @selection-change="handleSelectionChange">

                        <el-table-column label="账单号" prop="id" width="80" fixed>
                            <template slot-scope="scope">
                                <span>{{ scope.row.id }} </span>
                            </template>
                        </el-table-column>
                        <el-table-column label="货单号" prop="manifestId" width="80" fixed>
                            <template slot-scope="scope">
                                <span>{{ scope.row.manifestId }} </span>
                            </template>
                        </el-table-column>
                        <el-table-column label="折扣" prop="payoff" width="100">
                            <template slot-scope="scope">
                                <span>{{ scope.row.payoff }} </span>
                            </template>
                        </el-table-column>
                        <el-table-column label="应付金额" prop="amount" width="100">
                            <template slot-scope="scope">
                                <span>{{ scope.row.amount }} </span>
                            </template>
                        </el-table-column>
                        <el-table-column label="实付金额" prop="payment" width="100">
                            <template slot-scope="scope">
                                <span >{{ scope.row.payment }} </span>
                            </template>
                        </el-table-column>
                        <el-table-column label="支付方式" prop="payWay" width="100">
                            <template slot-scope="scope">
                                <span >{{ scope.row.payWay }} </span>
                            </template>
                        </el-table-column>
                        <el-table-column label="付款人" prop="payName" width="100">
                            <template slot-scope="scope">
                                <span>{{ scope.row.payName }} </span>
                            </template>
                        </el-table-column>
                        <el-table-column label="付款人电话" prop="payPhone" width="100">
                            <template slot-scope="scope">
                                <span>{{ scope.row.payPhone}} </span>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作时间" prop="date" width="150">
                            <template slot-scope="scope">
                                <span>{{ scope.row.date}} </span>
                            </template>
                        </el-table-column>

                        <el-table-column label="状态" prop="tag" width="120" fixed="right">
                            <template slot-scope="scope">
                                <el-tag :type="scope.row.tag "
                                        disable-transitions>{{scope.row.status}}</el-tag>
                            </template>
                        </el-table-column>
                    </el-table>
                    <template>
                        <el-pagination
                                @current-change="handleCurrentChange"
                                @size-change="handleSizeChange"
                                :current-page.sync="currentPage"
                                :page-sizes="[5, 10, 15, 20]"
                                :page-size="pageSize"
                                layout="total,sizes,prev, pager, next, jumper"
                                :total="tableData.length">
                        </el-pagination>
                    </template>
                </el-main>
            </el-container>
        </el-card>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: "BillManage",
        data() {
            return {
                //0未支付，1已支付，2已取消,3无需支付
                options3: [{
                    value: -1,
                    label: '全部'
                }, {
                    value: 0,
                    label: '未支付',
                },{
                    value: 1,
                    label: '已支付',
                },{
                    value: 2,
                    label: '已取消',
                }, {
                    value: 3,
                    label: '你无需支付',
                },
                ],
                condition:-1,
                //原始专案代码
                searchInput: {
                    //年龄输入框
                    age: "",
                    //名字输入框
                    name: "",
                    //名字输入框
                },
                //点击编辑按钮时，如果取消编辑，对信息进行备份还原
                originalAge:"0",
                originalName:'',
                //分页数据相关
                currentPage: 1,
                pageSize: 5,
                //备份数据表格，用于搜索完记录后，还原数据，只有删除才需要重新备份，因此在created或者mounted声生命周期函数深拷贝
                originalData: [],
                //表格数据相关
                tableData: [
                    // {
                    //     id: 1,
                    //     manifestId: 2,
                    //     payoff: 0.1,
                    //     amount: 0.0,
                    //     payment: 182.07,
                    //     bankCardId: 0,
                    //     status: 0,
                    //     tag:'warning',
                    //     payWay: 1,
                    //     payName: "张文祺",
                    //     payPhone: null,
                    //     userId: 1
                    // }
                       ],
                //选择框
                multipleSelection: [],
                //禁止状态，确保只有一个编辑按钮可点，当一个编辑按钮点亮后，其他编辑不可点
                forbid: false,
                //保存状态，内容为空禁止保存
                empty: false,
                //记录一个静态数据的最后一个id，为后续添加表格数据时，添加的id在此id的基础上进行相加
                addId:111,
                //信息表单
                validateForm: {
                    age: "",
                    name:""
                },
                //表单显示状态
                visible:false
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
            findByType(){
                const pageLoading = this.openLoading();
                axios.post('/mani-bill-get-list',{
                    type:this.condition
                })
                    .then(res => {
                        console.log(res.data);
                        if (res.data.code===0){
                            this.tableData=res.data.data.bill_list;
                            pageLoading.close();
                            this.$message.success("查询成功！");
                        }else{
                            let msg=res.data.message;
                            this.$message.error(msg);
                        }
                    })
                    .catch(error=>{
                        console.error(error);
                    });
                this.originalData = JSON.parse(JSON.stringify(this.tableData));

            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },

            //当前页数改变
            handleCurrentChange(val) {
                this.currentPage = val;

            },
            //页面展示的数据发生改变
            handleSizeChange(val) {
                this.pageSize = val;

            },
            //多选选择框
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
        },

        mounted() {
            axios.post('/mani/bill-get-list',{
                type:this.condition
            })
                .then(res => {
                    console.log(res.data);
                    if (res.data.code===0){
                        this.tableData=res.data.data.bill_list
                    }else{
                        let msg=res.data.message;
                        this.$message.error(msg);
                    }
                })
                .catch(error=>{
                    console.error(error);
                });
            this.originalData = JSON.parse(JSON.stringify(this.tableData))
        },
    }
</script>

<style lang="less" scoped>
    .search{
        margin: 10px 0 0 600px;
    }
    .el-container {
        /*设置内部填充为0，几个布局元素之间没有间距*/
        padding: 0px !important;
        /*外部间距也是如此设置*/
        margin: 0px !important;
        /*统一设置高度为100%*/
        height: 100vh;
    }
    #content {
        min-width: 900px;
    }
    #content .el-header {
        margin-top: 20px;
        line-height: 60px;
    }
    #content .el-header .el-button {
        margin-top: 10px;
        width: 100px;
    }
    .el-row .el-col {
        margin-left: 20px;
    }
    .el-pagination {
        position: relative;
        bottom: 0;
        height: 60px;
        line-height: 60px;
        text-align: center;
        padding: 15px 5px 0;
    }

    .box-card {
        width: 1000px;
        height: 650px;
        margin: 10px 0 0 30px;
        border: 1px solid #1f808c;

    }
    .userTitle{
        text-align: center;
        color:#112f50;
    }
    .inputSize{
        width: 150px;
    }
</style>