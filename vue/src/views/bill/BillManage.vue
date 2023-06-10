<template>
    <div>
        <el-card class="box-card" shadow="hover">
            <h1>账单管理</h1>
            <el-container id="content">
                <el-header>
                    <el-row>
                        <el-col :span="10">
                            <el-button type="danger" @click="delAll" style="float: left">删除货单</el-button>
                            <el-popover
                                    v-model="visible"
                                    placement="right"
                                    width="400"
                                    trigger="click"
                                    :open-delay="200"
                                    :close-delay="200"
                            >
                                <el-form
                                        :model="validateForm"
                                        ref="validateForm"
                                        label-width="100px"
                                        class="demo-ruleForm"
                                >
                                    <el-form-item
                                            label="年龄"
                                            prop="age"
                                            :rules="[{ required: true, message: '年龄不能为空' }]"
                                    >
                                        <el-input
                                                type="age"
                                                v-model="validateForm.age"
                                                autocomplete="off"
                                        ></el-input>

                                    </el-form-item>
                                    <el-form-item
                                            label="名称"
                                            prop="name"
                                            :rules="[{ required: true, message: '姓名不能为空' }]"
                                    >
                                        <el-input
                                                type="name"
                                                v-model="validateForm.name"
                                                autocomplete="off"
                                        ></el-input>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button type="primary" @click="submitForm('validateForm')">提交</el-button>
                                        <el-button @click="resetForm('validateForm')">重置</el-button>
                                    </el-form-item>
                                </el-form>
                                <el-button slot="reference" type="primary" style="margin-left:30px ">修改地址</el-button>
                            </el-popover>
                        </el-col>
                        <el-col :span="6">
                            <el-input
                                    clearable
                                    v-model="searchInput.age"
                                    placeholder="请输入年龄搜索"
                                    @input="searchCommon"
                            ></el-input>
                        </el-col>
                        <el-col :span="6">
                            <el-input
                                    clearable
                                    v-model="searchInput.name"
                                    placeholder="请输入名字搜索"
                                    @input="searchCommon"
                            ></el-input>
                        </el-col>
                    </el-row>
                </el-header>
                <el-main>
                    <el-table
                            :data="tableData.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize)"
                            style="width: 100%"
                            stripe
                            @selection-change="handleSelectionChange"
                    >
                        <el-table-column type="selection" width="55"> </el-table-column>

                        <el-table-column label="年龄" prop="age">
                            <template slot-scope="scope">
                                <el-input v-if="scope.row.editable" v-model="scope.row.age" @input="inputCodeRow(scope.$index, scope.row)">{{
                                    scope.row.age
                                    }}</el-input>
                                <span v-else
                                >{{ scope.row.age }}
              </span>
                            </template>
                        </el-table-column>

                        <el-table-column label="名字" prop="name">
                            <template slot-scope="scope">
                                <el-input v-if="scope.row.editable" v-model="scope.row.name" @input="inputCodeRow(scope.$index, scope.row)">{{
                                    scope.row.name
                                    }}</el-input>
                                <span  v-else
                                >{{ scope.row.name }}
              </span>
                            </template>
                        </el-table-column>


                        <el-table-column align="right" label="操作">
                            <template slot-scope="scope">
                                <el-button
                                        v-show="!scope.row.editable"
                                        size="mini"
                                        type="danger"
                                        @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                                <el-button
                                        v-show="!scope.row.editable"
                                        size="mini"
                                        type="primary"
                                        @click="handleDelete(scope.$index, scope.row)">查看详情</el-button>
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
                                :total="tableData.length"
                        >
                        </el-pagination>
                    </template>
                </el-main>
            </el-container>
        </el-card>
    </div>
</template>

<script>
    export default {
        name: "BillManage",
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
                    {
                        id:100,
                        age:"20",
                        name:'李四',
                        editable:false,
                    },
                    {
                        id:101,
                        age:"20",
                        name:'张三',
                        editable:false,
                    },
                    {
                        id:102,
                        age:"20",
                        name:'王五',
                        editable:false,
                    },
                    {
                        id:103,
                        age:"18",
                        name:'张三',
                        editable:false,
                    },
                    {
                        id:104,
                        age:"10",
                        name:'小陈',
                        editable:false,
                    },
                    {
                        id:105,
                        age:"20",
                        name:'小陈',
                        editable:false,
                    },
                    {
                        id:106,
                        age:"25",
                        name:'小丽',
                        editable:false,
                    },
                    {
                        id:107,
                        age:"20",
                        name:'张三',
                        editable:false,
                    },
                    {
                        id:108,
                        age:"21",
                        name:'张三',
                        editable:false,
                    },
                    {
                        id:109,
                        age:"30",
                        name:'张三',
                        editable:false,
                    },
                    {
                        id:110,
                        age:"45",
                        name:'麻子',
                        editable:false,
                    },
                    {
                        id:111,
                        age:"40",
                        name:'狗二蛋',
                        editable:false,
                    }],
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
            //人员单个删除
            handleDelete(index, row) {
                //获取要删除的id，row是element-ui封装好的点击的时候能获取你点击那一行的信息
                var delId = row.id
                this.$confirm("此操作将永久删除该文件, 是否继续?", "警告", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                }).then(() => {
                    //遍历表格，筛选掉表格中符合要删除的id，而不符合的返回给table，达到删除的目的
                    this.tableData = this.tableData.filter(ele =>
                        ele["id"] != delId //或者可以写成ele.id != delId
                    )
                    //需要将最新的表格数据传递给备份记录，这一步操作是解决搜索完东西后，表格的还原
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

            //搜索函数，该函数能在前一个搜索的基础上进行二次搜索，可以自定义多个搜索，在此函数的基础上添加即可
            searchCommon() {
                this.forbid = false
                this.tableData = JSON.parse(JSON.stringify(this.originalData))
                //搜索出的数据
                var searchList = []
                for(var key in this.searchInput){
                    /*这里有一点需要主要的是，this.tableDate内每一个对象的id健值还有name健值
                    跟输入框的this.searchInput内的age和name同名 所有才可以直接用for(var key in this.searchInput)中的key否则不可以*/
                    if(this.searchInput[key] != ""){ //遍历所有搜索框，如果某一项搜索框为空，直接跳过这个搜索框
                        searchList = this.tableData.filter(ele=>  {
                            ele.editable = false
                            // 忽略大小写匹配ele[key].toLowerCase().indexOf(this.searchInput[key].toLowerCase() != -1)，
                            // 但是对于数字而言没有任何效果，toLowerCase()只支持字符串  不支持数字类型 如果想匹配数字的话可以将数字变成字符串的数字 例如 1 => '1'
                            // 此外indexOf() 也是来匹配字符串而非其他类型，否则报错
                            return  ele[key].toLowerCase().indexOf(this.searchInput[key].toLowerCase()) >-1  //注意这里的xxx.indexOf(xxx) >-1,而不是xxx.indexOf(xx >-1) 不要括错位置了，否则出问题
                        })
                    }
                    else{
                        /* 这一步其实也并不完善，如果遇到了四个搜入框，1.3输入框内容为空，2.4输入框有内容，
                        那么会导致当遇到第三个输入框为空的情况下，将第二次输入框筛选出的东西给还原成数据表格元素模样
                        所以可以多选择添加一个表格，来记录被搜索框搜索出的东西，如果这么提示还不能理解的话，到时候我写出来
                        或者做一层判断只有searchList不为空才进行第368行否则不进行，就可以解决完上面说的问题*/
                        searchList =JSON.parse(JSON.stringify(this.tableData))
                    }
                    //将第一次过滤出的信息保留，留着下一次搜索框内容过滤，直到搜索框没有内容
                    this.tableData = JSON.parse(JSON.stringify(searchList))
                }
                //搜索框内容都为空那么allEmpty = true
                var allEmpty = true
                for(let key in this.searchInput){
                    if(this.searchInput[key]!==''){
                        //如果任意输入框存在一个为空的内容，那么allEmpty = false
                        allEmpty = false
                    }
                }
                //搜索内容都为空，通过备份的数据表格还原表格数据
                if(allEmpty){
                    this.tableData = JSON.parse(JSON.stringify(this.originalData))
                    this.tableData.forEach(e=>{
                        e.editable  = false
                    })
                    return this.tableData
                }
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
                this.visible = true
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.addId +=1
                        this.validateForm.id = this.addId,
                            this.validateForm.editable = false,
                            this.tableData.push(JSON.parse(JSON.stringify(this.validateForm)))
                        //添加完人员，将最新表格数据记录一份
                        this.originalData = JSON.parse(JSON.stringify(this.tableData))
                        //每次添加完后都重置一下表单
                        this.$refs[formName].resetFields();
                        this.visible = false
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
        },

        mounted() {
            this.originalData = JSON.parse(JSON.stringify(this.tableData))
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
</style>