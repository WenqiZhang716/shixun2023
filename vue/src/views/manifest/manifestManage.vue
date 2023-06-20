<template>
    <div>
        <el-card class="box-card" shadow="hover">
        <h1 class="userTitle">货单管理</h1>
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
                        stripe>
                    <el-table-column label="货单号" prop="id"  width="100" fixed>
                        <template slot-scope="scope">
                            <span >{{ scope.row.id }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="物品种类" prop="goodsType"  width="100">
                        <template slot-scope="scope">
                            <span >{{ scope.row.goodTypeId }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="重量" prop="weight"  width="100">
                        <template slot-scope="scope">
                            <span >{{ scope.row.weight }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="运输方式" prop="transportType"  width="100">
                        <template slot-scope="scope">
                            <span >{{ scope.row.transportType }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="开始地址" prop="beginAddress" width="200">
                        <template slot-scope="scope">
                            <span >{{ scope.row.beginAddress }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="结束地址" prop="endAddress" width="200">
                        <template slot-scope="scope">
                            <span >{{ scope.row.endAddress }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="支付方式" prop="payType"  width="100">
                        <template slot-scope="scope">
                            <span >{{ scope.row.payType }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="金额" prop="amount"  width="100">
                        <template slot-scope="scope">
                            <span >{{ scope.row.amount }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="收货人姓名" prop="receiverName" width="100">
                        <template slot-scope="scope">
                            <span>{{ scope.row.receiverName }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="收货人电话" prop="receiverPhone" width="150">
                        <template slot-scope="scope">
                            <span>{{ scope.row.receiverPhone }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="创建时间" prop="createDate" width="150">
                        <template slot-scope="scope">
                            <span>{{ scope.row.createDate }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="备注" prop="beizhu" width="200">
                        <template slot-scope="scope">
                            <span>{{ scope.row.beizhu }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="运输状态" prop="tag"  width="100" fixed="right">
                        <template slot-scope="scope">
                            <el-tag :type="scope.row.tag"
                                    disable-transitions>{{scope.row.status}}</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column label="支付状态" prop="tag2"  width="100" fixed="right">
                        <template slot-scope="scope">
                            <el-tag :type="scope.row.tag2"
                                    disable-transitions>{{scope.row.payStatus}}</el-tag>
                        </template>
                    </el-table-column>

                    <el-table-column  label="操作" width="250" fixed="right">
                        <template slot-scope="scope">
                            <el-button
                                    size="mini"
                                    type="primary"
                                    @click="showDetail(scope.row.id)">查看</el-button>
                            <el-button
                                    v-show="scope.row.isPay===0 && scope.row.payType==='先付后到' && scope.row.status!=='已取消'"
                                    size="mini"
                                    type="success"
                                    @click="payPage(scope.row.id)">支付</el-button>
                            <el-button
                                    v-show="scope.row.isPay===0 && scope.row.status==='未收件'"
                                    size="mini"
                                    type="danger"
                                    @click="cancelCheck(scope.row.id)">取消</el-button>

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


        <el-dialog  :visible.sync="detailFormVisible" class="dialogForm">
            <div>
                <el-form :model="detailForm" :rules="rules" ref="ruleForm" label-width="100px" class="descriptionStyle" >
                    <el-form-item prop="manifest">
                        <el-descriptions  title="对应账单" column="2" :model="detailForm.manifest" border>

                            <el-descriptions-item label="货单号"><span>{{detailForm.manifest.id}}</span></el-descriptions-item>
                            <el-descriptions-item label="物品种类"><span>{{detailForm.manifest.goodTypeId}}</span></el-descriptions-item>
                            <el-descriptions-item label="重量"><span>{{detailForm.manifest.weight}}</span></el-descriptions-item>
                            <el-descriptions-item label="运输方式"><span>{{detailForm.manifest.transportType}}</span></el-descriptions-item>
                            <el-descriptions-item label="开始地址"><span>{{detailForm.manifest.beginAddress}}</span></el-descriptions-item>
                            <el-descriptions-item label="结束地址"><span>{{detailForm.manifest.endAddress}}</span></el-descriptions-item>
                            <el-descriptions-item label="收货人姓名"><span>{{detailForm.manifest.receiverName}}</span></el-descriptions-item>
                            <el-descriptions-item label="收货人电话"><span>{{detailForm.manifest.receiverPhone}}</span></el-descriptions-item>
                            <el-descriptions-item label="创建时间"><span>{{detailForm.manifest.createDate}}</span></el-descriptions-item>
                            <el-descriptions-item label="备注"><span>{{detailForm.manifest.beizhu}}</span></el-descriptions-item>
                            <el-descriptions-item label="运送状态"><span>{{detailForm.manifest.status}}</span></el-descriptions-item>
                        </el-descriptions>
                    </el-form-item>

                    <el-form-item prop="manifest">
                        <el-descriptions  title="对应账单" column="2" :model="detailForm.bill" border>
                            <el-descriptions-item label="账单号"><span>{{detailForm.bill.id}}</span></el-descriptions-item>
                            <el-descriptions-item label="折扣"><span>{{detailForm.bill.payoff}}</span></el-descriptions-item>
                            <el-descriptions-item label="应付金额"><span>{{detailForm.bill.amount}}</span></el-descriptions-item>
                            <el-descriptions-item label="实付金额"><span>{{detailForm.bill.payment}}</span></el-descriptions-item>
                            <el-descriptions-item label="支付方式"><span>{{detailForm.bill.payWay}}</span></el-descriptions-item>
                            <el-descriptions-item label="支付人"><span>{{detailForm.bill.payName}}</span></el-descriptions-item>
                            <el-descriptions-item label="支付人电话"><span>{{detailForm.bill.payPhone}}</span></el-descriptions-item>
                            <el-descriptions-item label="支付状态"><span>{{detailForm.bill.status}}</span></el-descriptions-item>
                        </el-descriptions>
                    </el-form-item>
                </el-form>
            </div>
            <div slot="footer" class="dialog-footer">
                <el-button type="success" @click="checkTransport" v-show="this.detailForm.manifest.status!=='已取消'">查看运输状态</el-button>
                <el-button @click="detailFormVisible=false">关闭</el-button>
            </div>
        </el-dialog>

        <el-dialog title="提示" :visible.sync="dialogCancelVisible" width="30%" :before-close="handleClose">
            <span>确定取消？</span>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogCancelVisible = false">取 消</el-button>
    <el-button type="primary" @click=cancelOneManifest>确 定</el-button>
  </span>
        </el-dialog>

        <el-dialog title="支付账单" :visible.sync="dialogPayVisible" :before-close="handleClose" class="dialogForm">
            <el-form :model="payForm">
                <el-form-item label="选择银行卡: "
                              prop="cardList"
                              :rules="[ { required: true, trigger: 'blur' } ]">
                    <el-select v-model="payForm.card_order" placeholder="请选择" class="inputSize">
                        <el-option
                                v-for="item in cardOption"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value-1"
                                :disabled="item.disabled">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="银行卡密码："
                              :rules="[ { required: true, message: '请输入密码', trigger: 'blur' } ]">
                    <el-input type="password" v-model="payForm.password" class="inputSize"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="payIt">确 定</el-button>
                <el-button @click="dialogPayVisible=false">取 消</el-button>

            </div>
        </el-dialog>
    </div>
</template>

<script>
    import axios from "axios";
    export default {
        name: "manifestManage",

        data() {
            return {
                cardOption:[],
                payForm:{
                    bill_id:0,
                    card_order:0,
                    password:''
                },
                chooseId:0,
                detailFormVisible:false,
                dialogCancelVisible:false,
                dialogPayVisible:false,
                detailForm:{

                    manifest:{
                        id:100,
                        userId:1,
                        goodTypeId:1,
                        weight:20.23,
                        transportType:1,
                        beginAddress:'aaa',
                        beginId:0,
                        endAddress:"aaa",
                        endId:0,
                        payType:1,
                        amount:202,
                        receiverPhone:"1111",
                        receiverName:"张文祺",
                        beizhu:"暂无",
                        status:3,
                        tag:"danger",
                        createDate:"aaa",
                        isPay:0
                    },
                    bill:{
                        id:0,
                        manifestId:0,
                        payoff:0,
                        amount:0,
                        payment:0,
                        bankCardId:0,
                        payWay:0, //先付后到，先到后付
                        payName:0,
                        payPhone:"123456",
                        status:0,
                        userId:0
                    }
                },
                //0未发货、1进行中、2已完成、3已取消
                options3: [{
                    value: -1,
                    label: '全部'
                }, {
                    value: 4,
                    label: '待支付',
                },{
                    value:5,
                    label:'已支付',
                },{
                    value:6,
                    label:'无需支付',
                },{
                    value: 1,
                    label: '进行中',
                },{
                    value: 2,
                    label: '已完成',
                }, {
                    value: 3,
                    label: '已取消',
                    },
                ],
                condition:-1,
                currentPage: 1,
                pageSize: 5,
                //表格数据相关
                tableData: [
                    // {
                    //     id:100,
                    //     userId:1,
                    //     goodTypeId:1,
                    //     weight:20.23,
                    //     transportType:1,
                    //     beginAddress:'aaa',
                    //     beginId:0,
                    //     endAddress:"aaa",
                    //     endId:0,
                    //     payType:1,
                    //     amount:202,
                    //     receiverPhone:"1111",
                    //     receiverName:"张文祺",
                    //     beizhu:"暂无",
                    //     status:3,
                    //     tag:"danger",
                    //     createDate:"aaa",
                    //     isPay:0
                    // }
                    ],
                //表单显示状态
                visible:false
            };
        },
        methods: {
            checkTransport(){
                localStorage.setItem("manifestId",this.detailForm.manifest.id);
                this.$router.push('/transport/transport-plan');
            },
            payIt(){
                axios.post('/mani/bill-pay-bill', {
                    bill_id: this.payForm.bill_id,
                    card_order:this.payForm.card_order+1,
                    password:this.payForm.password
                })
                    .then(res => {
                        console.log(res.data);
                        if (res.data.code === 0) {
                            this.dialogPayVisible=false;
                            this.$message.success("支付成功！跳转到进度页面...");
                            this.$router.push("/transport/transport-plan");
                            localStorage.setItem("manifestId",this.payForm.bill_id)

                        } else {
                            let msg = res.data.message;
                            this.$message.error(msg);
                        }
                    })
                    .catch(error => {
                        console.error(error);
                    });
            },
            payPage(id){
                this.payForm.bill_id=id;
                this.dialogPayVisible=true;
            },
            handleClose(done) {
                this.$confirm('确认关闭？')
                    .then(_ => {
                        done();
                        console.log(_);
                    })
                    .catch(_ => {
                        console.error(_);
                    });
            },
            cancelOneManifest(){
                axios.post('/mani/manifest-cancel',{
                    id:this.chooseId
                })
                    .then(res => {
                        console.log(res.data);
                        if (res.data.code===0){
                            this.$message.success("取消支付成功!");
                            this.dialogCancelVisible=false;
                        }else{
                            let msg=res.data.message;
                            this.$message.error(msg);
                        }
                    })
                    .catch(error=>{
                        console.error(error);
                    });

                setTimeout(()=>{
                    axios.post('/transport/deleteOne',{
                        manifest_id:this.chooseId
                    })
                        .then(res => {
                            console.log(res.data);
                            if (res.data.code===0){
                                this.$message.success("删除规划路线成功!");
                                this.dialogCancelVisible=false;
                            }else{
                                let msg=res.data.message;
                                this.$message.error(msg);
                            }
                        })
                        .catch(error=>{
                            console.error(error);
                        });
                },1000);

                setTimeout(()=>{
                const pageLoading = this.openLoading();
                this.originIt();
                pageLoading.close();
                },1000);
            },

            cancelCheck(id){
                this.chooseId=id;
                this.dialogCancelVisible=true;
            },
            showDetail(id){
                const pageLoading = this.openLoading();
                axios.post('/mani/manifest-get-one-detail',{
                    id:id
                })
                    .then(res => {
                        console.log(res.data);
                        if (res.data.code===0){
                            this.detailForm=res.data.data.info;
                            pageLoading.close();
                            this.$message.success("查询成功!");
                            this.detailFormVisible=true;
                        }else{
                            let msg=res.data.message;
                            this.$message.error(msg);
                        }
                    })
                    .catch(error=>{
                        console.error(error);
                    });

            },
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
                axios.post('/mani/manifest-find',{
                    type:this.condition
                })
                    .then(res => {
                        console.log(res.data);
                        if (res.data.code===0){
                            this.tableData=res.data.data.manifest_list;
                            pageLoading.close();
                            this.$message.success("查询成功!");
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
            //当前页数改变
            handleCurrentChange(val) {
                this.currentPage = val;

            },
            //页面展示的数据发生改变
            handleSizeChange(val) {
                this.pageSize = val;

            },
            originIt(){
                axios.post('/mani/manifest-find',{
                    type:this.condition
                })
                    .then(res => {
                        console.log(res.data);
                        if (res.data.code===0){
                            this.tableData=res.data.data.manifest_list
                        }else{
                            let msg=res.data.message;
                            this.$message.error(msg);
                        }
                    })
                    .catch(error=>{
                        console.error(error);
                    });
            }

        },

        mounted() {
            this.originIt();
            axios.post('/bank-card/get-card-choose-list')
                .then(res => {
                    console.log(res.data);
                    if (res.data.code===0){
                        this.cardOption=res.data.data.bankCardList
                    }else{
                        let msg=res.data.message;
                        this.$message.error(msg);
                    }
                })
                .catch(error=>{
                    console.error(error);
                });
        },
    }
</script>

<style scoped>
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
    .inputSize{
        width: 150px;
    }
    .userTitle{
        text-align: center;
        color:#112f50;
    }
    .descriptionStyle{
        width: 580px;
        align-self: center;

    }



</style>