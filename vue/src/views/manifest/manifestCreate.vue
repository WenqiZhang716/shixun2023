<template>
   <div>
       <el-card class="box-card" shadow="hover">
       <h1 class="userTitle">创建订单</h1>
       <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">

           <el-form-item label="物品种类: "
                         prop="good_type"
                         :rules="[ { required: true, trigger: 'blur' } ]">
               <el-select v-model="ruleForm.goods_type" placeholder="请选择" class="inputSize2">
                   <el-option
                           v-for="item in options"
                           :key="item.value"
                           :label="item.label"
                           :value="item.value-1"
                           :disabled="item.disabled">
                   </el-option>
               </el-select>
           </el-form-item>

           <el-form-item label="重量（kg): "
                         prop="weight"
                         :rules="[ { required: true, message: '请输入物品重量', trigger: 'blur' } ]">
               <el-input type="number" v-model="ruleForm.weight"  class="inputSize2" pattern="\d*" ></el-input>
           </el-form-item>

           <el-form-item label="运输种类: "
                         prop="transport_type"
                         :rules="[ { required: true, trigger: 'blur' } ]">
               <el-select v-model="ruleForm.transport_type" placeholder="请选择" class="inputSize2">
                   <el-option
                           v-for="item in options2"
                           :key="item.value"
                           :label="item.label"
                           :value="item.value"
                           :disabled="item.disabled">
                   </el-option>
               </el-select>
           </el-form-item>
           <el-form-item label="付款方式: "
                         prop="pay_type"
                         :rules="[ { required: true, trigger: 'blur' } ]">
               <el-select v-model="ruleForm.pay_type" placeholder="请选择" class="inputSize2">
                   <el-option
                           v-for="item in options3"
                           :key="item.value"
                           :label="item.label"
                           :value="item.value"
                           :disabled="item.disabled">
                   </el-option>
               </el-select>
           </el-form-item>
           <el-form-item label="开始地址: "
                         prop="beginChoose"
                         :rules="[ { required: true, trigger: 'blur' } ]">
               <el-select  v-model="firstSelectValue" @change="handleFirstSelectChange" class="inputSize2" id="begin1">
                   <el-option v-for="option in firstSelectOptions"
                              :key="option.value"
                              :label="option.label"
                              :value="option.value">
                   </el-option>
               </el-select>

               <el-select v-model="secondSelectValue"  class="inputSize2" id="end1">
                   <el-option v-for="option in secondSelectOptions"
                              :key="option.value"
                              :label="option.label"
                              :value="option.value"></el-option>
               </el-select>
           </el-form-item>
           <el-form-item label="详细地址: "
                         prop="begin_address"
                         :rules="[ { required: true, message: '请输入开始地址', trigger: 'blur' } ]">
               <el-input v-model="ruleForm.begin_address"  class="inputSize" ></el-input>
           </el-form-item>

           <el-form-item label="结束地址: "
                         prop="endChoose" :rules="[ { required: true, trigger: 'blur' } ]">
               <el-select  v-model="firstSelectValue2" @change="handleFirstSelectChange2" class="inputSize2" id="begin2">
                   <el-option v-for="option in firstSelectOptions2"
                              :key="option.value"
                              :label="option.label"
                              :value="option.value">
                   </el-option>
               </el-select>
               <el-select v-model="secondSelectValue2"  class="inputSize2" id="end2">
                   <el-option v-for="option in secondSelectOptions2"
                              :key="option.value"
                              :label="option.label"
                              :value="option.value"></el-option>
               </el-select>
           </el-form-item>
           <el-form-item label="详细地址: "
                         prop="end_address"
                         :rules="[ { required: true, message: '请输入结束地址', trigger: 'blur' } ]">
               <el-input v-model="ruleForm.end_address"  class="inputSize" ></el-input>
           </el-form-item>



           <el-form-item label="收货人姓名: "
                         prop="receiver_name"
                         :rules="[ { required: true, message: '请输入收货人姓名', trigger: 'blur' } ]">
               <el-input v-model="ruleForm.receiver_name"  class="inputSize" ></el-input>
           </el-form-item>

           <el-form-item label="收货人手机: "
                         prop="receiver_phone"
                         :rules="[ { required: true, message: '请输入收货人手机号', trigger: 'blur' } ]">
               <el-input v-model="ruleForm.receiver_phone"  class="inputSize" ></el-input>
           </el-form-item>

           <el-form-item label="备注: "
                         prop="beizhu">
               <el-input v-model="ruleForm.beizhu"
                         class="inputSize"
                         type="textarea"
                         :rows="4"
                         placeholder="请输入内容"></el-input>
           </el-form-item>

           <el-form-item>
               <el-button type="primary"
                          @click=submitCheck
                          :disabled="ruleForm.goods_type===''||ruleForm.transport_type===''||
                    ruleForm.weight===''||ruleForm.begin_address===''||ruleForm.end_address===''||
                    ruleForm.pay_type===''||ruleForm.receiver_name===''||ruleForm.receiver_phone===''"
               >立即创建</el-button>
               <el-button @click="resetForm('ruleForm')">重置</el-button>
           </el-form-item>
       </el-form>
       </el-card >

       <el-dialog title="提示" :visible.sync="dialogvisible" width="30%" :before-close="handleClose">
           <span>确定提交订单？</span>
           <span slot="footer" class="dialog-footer">
    <el-button @click="dialogvisible = false">取 消</el-button>
    <el-button type="primary" @click=submitForm>确 定</el-button>
  </span>
       </el-dialog>

       <el-dialog title="生成账单……" :visible.sync="dialogFormVisible" :before-close="handleClose" class="dialogForm">
           <div>
                   <el-descriptions class="descriptionStyle" column=1 ref="billForm" :model="billForm" border>
                       <el-descriptions-item label="账单号"><span>{{billForm.id}}</span></el-descriptions-item>
                       <el-descriptions-item label="货单号"><span>{{billForm.manifestId}}</span></el-descriptions-item>
                       <el-descriptions-item label="折扣"><span>{{billForm.payoff}}</span></el-descriptions-item>
                       <el-descriptions-item label="应付金额"><span>{{billForm.amount}}</span></el-descriptions-item>
                       <el-descriptions-item label="实付金额"><span>{{billForm.payment}}</span></el-descriptions-item>
                       <el-descriptions-item label="支付方式"><span>{{billForm.payWay}}</span></el-descriptions-item>
                       <el-descriptions-item label="支付人"><span>{{billForm.payName}}</span></el-descriptions-item>
                       <el-descriptions-item label="支付人电话"><span>{{billForm.payPhone}}</span></el-descriptions-item>
                       <el-descriptions-item label="支付状态"><span>{{billForm.status}}</span></el-descriptions-item>
                   </el-descriptions>
               <h2 v-if="billForm.payWay===1" class="userTitle">创建账单成功！请等待运货员上门取件！</h2>
           </div>
           <div slot="footer" class="dialog-footer">
               <el-button type="primary" @click=payIt :disabled="needPay===false">去支付</el-button>
               <el-button @click=cancelPay>稍后再说</el-button>
           </div>
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
               <el-button type="primary" @click=reallyPayIt>确 定</el-button>
               <el-button @click=cancelPayAgain>取 消</el-button>

           </div>
       </el-dialog>

   </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: "manifestSelection",
        data() {
            return {
                firstSelectValue: -1,
                secondSelectValue: -1,
                firstSelectValue2: -1,
                secondSelectValue2: -1,
                firstSelectOptions: [], // 第一个选择框的选项数据
                secondSelectOptions: [{
                    value:-1,
                    label:'请选择'
                }] ,
                firstSelectOptions2: [], // 第一个选择框的选项数据
                secondSelectOptions2: [{
                    value:-1,
                    label:'请选择'
                }] ,
                payForm:{
                    bill_id:0,
                    card_order:0,
                    password:''
                },
                cardOption:[],
                dialogFormVisible: false,
                dialogvisible:false,
                dialogPayVisible:false,
                needPay:true,
                billForm:{
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

                },
                ruleForm: {
                    goods_type:0,
                    transport_type:0,
                    weight:0,
                    begin_address:'',
                    end_address:'',
                    pay_type:0,
                    receiver_name:'',
                    receiver_phone:'',
                    beizhu:'暂无'

                },
                //物品种类（需要获取）
                options: [],
                //运输方式
                options2: [{
                    value: 0,
                    label: '普通件'
                }, {
                    value: 1,
                    label: '快件',
                }, {
                    value: 2,
                    label: '超快件'
                }],
                //付款方式
                options3: [{
                    value: 0,
                    label: '先付后到'
                }, {
                    value: 1,
                    label: '先到后付',
                }],
                rules: {},
            };

        },
        mounted() {
            axios.post('/transport/big-step-list')
                .then(res => {
                    console.log(res.data);
                    if (res.data.code===0){
                        this.firstSelectOptions=res.data.data.info
                    }else{
                        let msg=res.data.message;
                        this.$message.error(msg);
                    }
                })
                .catch(error=>{
                    console.error(error);
                });

            axios.post('/transport/big-step-list')
                .then(res => {
                    console.log(res.data);
                    if (res.data.code===0){
                        this.firstSelectOptions2=res.data.data.info
                    }else{
                        let msg=res.data.message;
                        this.$message.error(msg);
                    }
                })
                .catch(error=>{
                    console.error(error);
                });

            axios.post('/mani/get-good-type-list')
                .then(res => {
                    console.log(res.data);
                    if (res.data.code===0){
                        this.options=res.data.data.list
                    }else{
                        let msg=res.data.message;
                        this.$message.error(msg);
                    }
                })
                .catch(error=>{
                    console.error(error);
                });


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
        methods: {
            handleFirstSelectChange2(){
                this.secondSelectValue2=-1;
                let id=this.firstSelectValue2;
                if(id==='-1'){
                    this.$message.error("请先选择省/直辖市！");
                }else{
                    // 向后端发送异步请求，获取第二个选择框的选项数据
                    axios.post('/transport/small-step-list', {
                        id:id
                    })
                        .then(response => {
                            this.secondSelectOptions2 = response.data.data.id;
                        })
                        .catch(error => {
                            console.error('Failed to get second select options:', error);
                        });
                }
            },
            handleFirstSelectChange() {
               this.secondSelectValue=-1;
                let id=this.firstSelectValue;
                if(id==='-1'){
                    this.$message.error("请先选择省/直辖市！");
                }else{
                    // 向后端发送异步请求，获取第二个选择框的选项数据
                    axios.post('/transport/small-step-list', {
                        id:id
                    })
                        .then(response => {
                            this.secondSelectOptions = response.data.data.id;
                        })
                        .catch(error => {
                            console.error('Failed to get second select options:', error);
                        });
                }

            },
            payIt(){
                this.dialogFormVisible=false;
                this.dialogPayVisible=true;
            },
            reallyPayIt(){
                axios.post('/bill/pay-bill', {
                    bill_id: this.billForm.manifestId,
                    card_order:this.payForm.card_order+1,
                    password:this.payForm.password
                })
                    .then(res => {
                        console.log(res.data);
                        if (res.data.code === 0) {
                            this.dialogPayVisible=false;
                            this.$message.success("支付成功！跳转到进度页面...");
                            this.$router.push("/transport/transport-plan");

                        } else {
                            let msg = res.data.message;
                            this.$message.error(msg);
                        }
                    })
                    .catch(error => {
                        console.error(error);
                    });

            },
            bulidTransportPlan(id){
                axios.post('/transport/path-planning', {
                    manifest_id: id
                })
                    .then(res => {
                        console.log(res.data);
                        if (res.data.code === 0) {
                            this.$message.success("规划路线成功！");
                            localStorage.setItem("manifestId",id)
                        } else {
                            let msg = res.data.message;
                            this.$message.error(msg);
                        }
                    })
                    .catch(error => {
                        console.error(error);
                    });
            },
            cancelPayAgain(){
                this.dialogPayVisible = false;
                this.$message.warning("取消账单支付,可选择稍后支付！");
                this.$router.push("/manifest/manage");
            },
            submitCheck() {
                axios.post('/user/userCheck')
                    .then(res => {
                        console.log(res.data);
                        if (res.data.code === 0) {
                            if (this.ruleForm.weight <= 0) {
                                this.$message.error("物体重量必须大于0！");
                            } else if(this.firstSelectValue===-1 || this.secondSelectValue===-1 || this.firstSelectValue2===-1 || this.secondSelectValue2===-1){
                                this.$message.error("请选择开始地址和结束地址的省/市！");
                            }else if(this.firstSelectValue===this.firstSelectValue2&&
                                this.secondSelectValue===this.secondSelectValue2&&
                                this.ruleForm.begin_address===this.ruleForm.end_address){
                                this.$message.error("请不要自己给自己家寄！");
                            }else{
                                const begin1=document.getElementById('begin1').value;
                                const begin2=document.getElementById('begin2').value;
                                const end1=document.getElementById('end1').value;
                                const end2=document.getElementById('end2').value;
                                this.ruleForm.begin_address=begin1+"_"+end1+"_"+this.ruleForm.begin_address;
                                this.ruleForm.end_address=begin2+"_"+end2+"_"+this.ruleForm.end_address;
                                // this.$message.error(this.ruleForm.begin_address+this.ruleForm.end_address);
                                this.dialogvisible=true;
                            }
                        } else if(res.data.code === 1) {
                            let msg = res.data.message;
                            this.$message.error(msg);
                            this.$router.push('/user/user-info');
                        }else{
                            let msg = res.data.message;
                            this.$message.error(msg);
                            this.$router.push('/user/bank-account');
                        }
                    })
                    .catch(error => {
                        console.error(error);
                    });


            },
            createBill(id){
                axios.post('/bill/create', {
                    manifest_id: id
                })
                    .then(res => {
                        console.log(res.data);
                        if (res.data.code === 0) {
                            this.$message.success("创建账单成功");
                        } else {
                            let msg = res.data.message;
                            this.$message.error(msg);
                        }
                    })
                    .catch(error => {
                        console.error(error);
                    });
            },
            getBill(id){
                axios.post('/bill/getOne', {
                    manifest_id:id
                })
                    .then(res => {
                        console.log(res.data);
                        if (res.data.code === 0) {
                                this.billForm.id=res.data.data.info.id;
                                 this.billForm.manifestId=id;
                                this.billForm.payoff=res.data.data.info.payoff;
                                this.billForm.amount=res.data.data.info.amount;
                                this.billForm.payment=res.data.data.info.payment;
                                this.billForm.bankCardId=res.data.data.info.bankCardId;
                                this.billForm.payWay=res.data.data.info.payWay===0?'先付后到':'先到后付';
                                this.billForm.payName=res.data.data.info.payName;
                                this.billForm.payPhone=res.data.data.info.payPhone;
                                this.billForm.status=res.data.data.info.payWay===0?'未支付':'请等对方支付';
                                this.billForm.userId=res.data.data.info.userId;
                                if(this.billForm.payWay===1){
                                    this.needPay=false;
                                }
                            setTimeout(()=>{
                                this.dialogFormVisible = true;
                            },1000);

                            console.log(this.billForm)
                        } else {
                            let msg = res.data.message;
                            this.$message.error(msg);
                        }
                    })
                    .catch(error => {
                        console.error(error);
                    });
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
            submitForm() {
                this.needPay=true;
               this.dialogvisible = false;
                let id = 0;
                axios.post('/mani/create', this.ruleForm)
                    .then(res => {
                        console.log(res.data);
                        if (res.data.code === 0) {
                            id = res.data.data.id;
                            this.$message.success("创建货单成功！");
                            this.createBill(id);
                            //规划运输路线
                            setTimeout(()=>{
                                this.bulidTransportPlan(id);
                            },1500);
                            //展示账单
                            setTimeout(()=>{
                                this.getBill(id);
                            },1500);

                        } else {
                            let msg = res.data.message;
                            this.$message.error(msg);
                        }
                    })
                    .catch(error => {
                        console.error(error);
                    });

            },
            resetForm(formName) {
                this.$message.success(this.ruleForm.transport_type);
                this.$refs[formName].resetFields();
            },
            cancelPay(){
                this.dialogvisible = false;
                this.$message.info("跳转到货单管理页面！");
                this.$router.push("/manifest/manage")
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
    .demo-ruleForm{
        width: 580px;
        margin: 40px 0 0 40px;
    }

    .inputSize{
        width: 300px;
    }
    .inputSize2{
        width: 150px;
    }
    .descriptionStyle{
        width: 380px;
        margin: 30px 0 0 100px;
    }
    /*.dialogForm{*/
    /*    width: 1080px;*/
    /*    margin: 20px 0 0 50px;*/
    /*    border: 1px solid #1f808c;*/
    /*}*/

</style>