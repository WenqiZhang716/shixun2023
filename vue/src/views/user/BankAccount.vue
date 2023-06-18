<template>
    <div>
        <el-card class="box-card" shadow="hover">
            <h1 class="userTitle">银行卡管理</h1>

            <el-container id="content">
                <!--header里面是按钮-->

                <el-header>
                    <el-row>
                        <el-col :span="30">
                            <el-popover
                                    v-model="visible"
                                    placement="right"
                                    width="400"
                                    trigger="click"
                                    :open-delay="200"
                                    :close-delay="200">
                                <h1 class="userTitle">新增一张银行卡</h1>
                                <el-form
                                        :model="validateForm"
                                        ref="validateForm"
                                        label-width="100px"
                                        class="demo-ruleForm">
                                    <el-form-item
                                            label="所属银行"
                                            prop="bankname"
                                            :rules="[{ required: true, message: '银行不能为空' }]">
                                        <el-input
                                                type="text"
                                                v-model="validateForm.bankname" autocomplete="off" ></el-input>

                                    </el-form-item>
                                    <el-form-item
                                            label="银行卡号"
                                            prop="cardNum"
                                            :rules="[{ required: true, message: '卡号不能为空' }]">
                                        <el-input type="name" v-model="validateForm.cardNum" autocomplete="off"></el-input>
                                    </el-form-item>

                                    <el-form-item>
                                        <el-button type="primary" @click="submitForm('validateForm')"
                                                   :disabled="validateForm.bankname===''||validateForm.cardNum===''||validateForm.password===''" round>提交</el-button>
                                        <el-button @click="resetForm('validateForm')" round>重置</el-button>
                                        <el-button type="warning" @click="visible=false" round>取消</el-button>
                                    </el-form-item>
                                </el-form>
                                    <el-button slot="reference" type="primary" :disabled="tableData.length>=5">添加</el-button>

                            </el-popover>

                        </el-col>
                    </el-row>
                </el-header>

                <el-main>
                    <el-table
                            :data="tableData" style="width: 100%" stripe @selection-change="handleSelectionChange">
<!--                        <el-table-column type="selection" width="55"> </el-table-column>-->
                        <!--定义列1-->
                        <el-table-column label="序号" prop="BankId" fixed>
                            <template slot-scope="scope">
                                <el-input v-if="scope.row.editable" v-model="scope.row.orders" @input="inputCodeRow(scope.$index, scope.row)">{{scope.row.orders }}</el-input>
                                <span v-else>{{ scope.row.orders }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="所属银行" prop="bank">
                            <template slot-scope="scope">
                                <el-input v-if="scope.row.editable" v-model="scope.row.bankName" @input="inputCodeRow(scope.$index, scope.row)">{{scope.row.bankName}}</el-input>
                                <span v-else>{{ scope.row.bankName }}</span>
                            </template>
                        </el-table-column>

                        <el-table-column label="卡号" prop="BankNum">
                            <template slot-scope="scope">
                                <el-input v-if="scope.row.editable" v-model="scope.row.cardNum" @input="inputCodeRow(scope.$index, scope.row)">{{scope.row.cardNum }}</el-input>
                                <span  v-else>{{ scope.row.cardNum}}</span>
                            </template>
                        </el-table-column>

                        <el-table-column label="余额" prop="amount">
                            <template slot-scope="scope">
                                <el-input v-if="scope.row.editable" v-model="scope.row.amount" @input="inputCodeRow(scope.$index, scope.row)">{{scope.row.amount }}</el-input>
                                <span  v-else>{{ scope.row.amount}}</span>
                            </template>
                        </el-table-column>


                        <el-table-column align="right" label="操作">
                            <template slot-scope="scope">
                                <el-button
                                        v-show="!scope.row.editable"
                                        size="mini"
                                        type="danger"
                                        @click="handleDelete(scope.$index, scope.row)" round>删除</el-button>

                            </template>
                        </el-table-column>
                    </el-table>
                    <template>
                        <h5 class="userTitle">注：最多能添加五张银行卡</h5>
                    </template>
<!--                    <template>-->
<!--                        <el-pagination-->
<!--                                @current-change="handleCurrentChange"-->
<!--                                @size-change="handleSizeChange"-->
<!--                                :current-page.sync="currentPage"-->
<!--                                :page-sizes="[5, 10, 15, 20]"-->
<!--                                :page-size="pageSize"-->
<!--                                layout="total,sizes,prev, pager, next, jumper"-->
<!--                                :total="tableData.length">-->
<!--                        </el-pagination>-->
<!--                    </template>-->
                </el-main>
            </el-container>
        </el-card>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: "BankAccount",
        data() {
            return {
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
                    //     orders:100,
                    //     bankName:'中国银行',
                    //     cardNum:'123456',
                    //     amount:84,
                    //     name:'李四',
                    //     editable:false,
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
                    bankname:'',
                    cardNum:''
                },
                //表单显示状态
                visible:false
            };
        },
       // created() {},

        methods: {
            //人员单个删除
            handleDelete(index, row) {
                this.$confirm("确定删除该银行卡信息?", "警告", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                }).then(() => {
                    console.log(row);
                    axios.post('/bank-card/delete',{
                        order:row.orders
                    })
                        .then(res => {
                            console.log(res.data);
                          //  let datas=res.data.data;
                            if (res.data.code===0){
                                this.$message({
                                    type: "success",
                                    message: "删除成功!",
                                })
                                this.getList();
                            }else{
                                let msg=res.data.message;
                                this.$message.error( msg);
                            }
                        })
                        .catch(error=>{
                            console.error(error);
                        });

                })
                    .catch(() => {
                        this.$message({
                            type: "info",
                            message: "已取消删除",
                        });
                    });
            },
            //人员批量删除
            delAll() {
                //没有点击选择框，点击删除直接提示
                if (this.multipleSelection.length == 0) {
                    this.$alert("选择框未选择，点击删除按钮无效", "警告", {
                        confirmButtonText: "确定",
                        callback: () => {
                            this.$message({
                                type: "info",
                                message: `删除失败`,
                            });
                        },
                    });
                    return;
                }
                this.$confirm("此操作将永久删除被选中的文件, 是否继续?", "警告", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                }).then(() => {
                    //遍历出所有选中的id，然后将表格数据中的id与选中的id做匹配，符合的删除
                    for(let key in this.multipleSelection){ //这里涉及到for(变量 in  数组) for(变量 of 对象 )
                        //for(变量 in 对象)    for(变量 of 对象)  变量具体指的是什么了。需要好好去了解一下
                        //更新表格数据，将删除信息的表格重新赋值
                        this.tableData = this.tableData.filter(ele=>
                            this.multipleSelection[key].id !=ele.id
                        )
                    }
                    //这里涉及深度拷贝知识，有不懂的可以查一下深度拷贝，浅拷贝，以及直接赋值的异同
                    this.originalData = JSON.parse(JSON.stringify(this.tableData))
                    this.$message({
                        type: "success",
                        message: "删除成功!",
                    })
                })
                    .catch(() => {
                        this.$message({
                            type: "info",
                            message: "已取消删除",
                        });
                    });
            },

            /*编辑函数 */
            handleEdit(index, row) {
                console.log(row)
                //这一步操作是确保只有一个编辑按钮可点，当点击了其中一个编辑按钮，其他编辑按钮应该不可点
                this.forbid = true
                //基础点击前的名字和年龄，如何用户点取消时还原数据
                this.originalAge = row.age
                this.originalName = row.name
                row.editable = true;
                if(row.age !='' && row.name!=''){ //只要俩个内容不为空，那么就可以点击保存按钮
                    this.empty = false //这一步操作是解决当某一项输入框为空，再点击取消，再点击编辑时，保存按钮不给保存的情况
                }
            },
            //编辑函数取消按钮
            handleCancel(index, row){
                this.$confirm("是否要取消编辑?", "警告", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                })
                    .then(() => {
                        this.forbid = false
                        row.age = this.originalAge
                        row.name = this.originalName
                        row.editable = false;
                        this.$message({
                            type: "success",
                            message: "取消编辑成功!",
                        });
                    })
                    .catch(() => {
                        this.$message({
                            type: "info",
                            message: "已取消编辑",
                        });
                    });
            },
            //编辑内容为空时，不给保存
            inputCodeRow(index,row){
                if(row.age === '' || row.name === ''){
                    this.empty = true //为空的时候，保存按钮不可点击
                }else{
                    this.empty = false
                }
            },
            //编辑函数保存按钮
            handleSave(index, row){
                //输入框跟原来输入框内容一致时,不做任何操作，
                //当改变了表格，那么就将备份的数据表格记录成最新的状态防止搜索的时候以旧的表格做为模板进行搜索
                this.originalData = JSON.parse(JSON.stringify(this.tableData))
                row.editable = false
                this.forbid = false
                return
            },

            //添加人员
            submitForm(formName) {
                this.visible = true;
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        axios.post('/bank-card/add-bankcard',{
                            bank_card:this.validateForm.cardNum,
                            bank_name:this.validateForm.bankname
                        })
                            .then(res => {
                                console.log(res.data);
                                if (res.data.code===0){
                                    this.$message.success('添加银行卡成功！');
                                    this.addId +=1;
                                    this.validateForm.id = this.addId;
                                    this.validateForm.editable = false;
                                    let item={
                                        cardNum:this.validateForm.cardNum,
                                        bankName:this.validateForm.bankname,
                                        amount:1000,
                                        orders:this.tableData.length+1
                                    };
                                    this.tableData.push(JSON.parse(JSON.stringify(item)));
                                    //添加完人员，将最新表格数据记录一份
                                    this.originalData = JSON.parse(JSON.stringify(this.tableData));
                                    //每次添加完后都重置一下表单
                                    this.$refs[formName].resetFields();
                                    this.visible = false

                                }else{
                                    let msg=res.data.message;
                                    this.$message.error(msg);
                                }
                            })
                            .catch(error=>{
                                console.error(error);
                            });
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
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
            getList(){
                axios.post('/bank-card/get-card-list')
                    .then(res => {
                        console.log(res.data);
                        let datas=res.data.data;
                        if (res.data.code===0){
                            this.tableData=datas.bankCardList;
                        }else{
                            let msg=res.data.message;
                            this.$message.error( msg);
                        }
                    })
                    .catch(error=>{
                        console.error(error);
                    });
                this.originalData = JSON.parse(JSON.stringify(this.tableData))
            }
        },
        mounted() {
          this.getList();
        },
    }
</script>

<style lang="less" scoped>
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
        height: 550px;
        margin: 10px 0 0 30px;
        border: 1px solid #1f808c;

    }
    .userTitle{
        text-align: center;
        color:#112f50;
    }
</style>