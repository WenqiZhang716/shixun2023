<template>
    <div>
        <el-card class="box-card" shadow="hover">
            <div class="userAvatar">
                <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
            </div>
        <h1 class="userTitle">用户基础信息</h1>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item v-if="isInput.readOnly" label="手机号: " prop="phone">
                    <span v-if="isInput.readOnly">{{ruleForm.name}}</span>
            </el-form-item>
            <el-form-item label="昵称: " prop="nickname">
                <span v-if="isInput.readOnly">{{ruleForm.nick}}</span>
                <el-input v-else v-model="ruleForm.nick"  class="inputSize" id="nick"></el-input>
            </el-form-item>
            <el-form-item label="真实姓名: " prop="realName">
                <span v-if="isInput.readOnly">{{ruleForm.realName}}</span>
                <el-input v-else v-model="ruleForm.realName"  class="inputSize" id="realName"></el-input>
            </el-form-item>

            <el-form-item label="性别: " prop="sex">
                <span v-if="isInput.readOnly">{{ruleForm.sex}}</span>
<!--                <el-input v-else v-model="ruleForm.sex"  class="inputSize2" id="sex"></el-input>-->
                    <el-select v-else v-model="value" placeholder="请选择" id="sex" class="inputSize2">
                        <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                                :disabled="item.disabled">
                        </el-option>
                    </el-select>

            </el-form-item>

            <el-form-item label="年龄: " prop="age">
                <span v-if="isInput.readOnly">{{ruleForm.age}}</span>
                <el-input v-else v-model="ruleForm.age"  class="inputSize2" id="age" type="number"></el-input>
            </el-form-item>

<!--            <el-form-item label="默认地址: " prop="address">-->
<!--                <span v-if="isInput.readOnly">{{ruleForm.address}}</span>-->
<!--                <el-input v-else v-model="ruleForm.address"  class="inputSize" id="address"></el-input>-->
<!--            </el-form-item>-->

            <el-form-item v-if="isInput.readOnly" label="是否实名: " prop="isCheck">
                <span v-if="isInput.readOnly">{{ruleForm.check}}</span>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm')" v-if="isInput.readOnly">修改个人信息</el-button>
                <el-button type="primary" @click="submitSure()" v-if="isInput.readOnly===false" >确定</el-button>
                <el-button type="warning" @click="resetForm('ruleForm')" v-if="isInput.readOnly===false" >重置</el-button>
                <el-button type="danger" @click="returnRead" v-if="isInput.readOnly===false">取消</el-button>
                <el-button type="success" :disabled="ruleForm.check==='是'" v-if="isInput.readOnly" @click=" dialogFormVisible = true">进行实名认证</el-button>
            </el-form-item>
        </el-form>
        </el-card>
        <el-dialog title="实名认证" :visible.sync="dialogFormVisible" class="dialogForm">
            <el-form :model="Checkform">
                <el-form-item label="真实姓名"
                              :label-width="formLabelWidth"
                              :rules="[ { required: true, message: '请输入真实姓名', trigger: 'blur' } ]">
                    <el-input v-model="Checkform.realname"  class="inputSize"></el-input>
                </el-form-item>
                <el-form-item label="身份证号"
                              :label-width="formLabelWidth"
                              :rules="[ { required: true, message: '请输入身份证号', trigger: 'blur' } ]">
                    <el-input v-model="Checkform.IdNumber"  class="inputSize"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click=CheckOne  :disabled="Checkform.realname===''||Checkform.IdNumber===''" >确 定</el-button>
            </div>
        </el-dialog>
    </div>

</template>

<script>
    import axios from "axios";
    export default {
        name: "UserInformation",
        data() {
            return {
                options: [{
                    value: 0,
                    label: '保密'
                }, {
                    value: 1,
                    label: '女',
                }, {
                    value: 2,
                    label: '男'
                }],
                value: 0,
                ruleForm: {
                    name: '',
                    nick:'',
                    realName:'',
                    sex:'',
                    age:0,
                    address:'',
                    check:'是'
                },
                isInput:{
                    readOnly:true
                },
                rules: {
                    nick: [
                        {   message: '请输入昵称', trigger: 'change' }
                    ],
                    address: [
                        {   message: '请输入默认地址', trigger: 'change' }
                    ],
                    realName: [
                        {   message: '请输入真实姓名', trigger: 'change' }
                    ],
                    sex: [
                        {   message: '请输入性别', trigger: 'change' }
                    ],
                    age: [
                        {   message: '请输入年龄', trigger: 'change' }
                    ],

                },
                dialogFormVisible: false,
                Checkform: {
                    realname: '',
                    IdNumber: '',
                },
                formLabelWidth: '80px'
            };
        },
        //数据的初始化
        created(){
            axios.post('/auth/get-info')
                .then(res => {
                    console.log(res.data);
                    let datas=res.data.data;
                    if (res.data.code===0){
                        this.ruleForm.realName=datas.realName===null?"未填写":datas.realName;
                        this.ruleForm.address=datas.address===null?"暂无":datas.address;
                        this.ruleForm.nick=datas.nickname;
                        this.ruleForm.check= datas.isCheck===1?'是':'否';
                        this.ruleForm.name=datas.name;
                        this.ruleForm.age= datas.age;
                        if(datas.sex===0){
                            this.ruleForm.sex="未知";
                        }else if(datas.sex===1){
                            this.ruleForm.sex="女";
                        }else{
                            this.ruleForm.sex="男";
                        }

                    }else{
                        let msg=res.data.message;
                        this.$message.error( msg);
                    }
                })
                .catch(error=>{
                    console.error(error);
                });
        },
        methods: {
            reflushIt(){
                axios.post('/auth/get-info')
                    .then(res => {
                        console.log(res.data);
                        let datas=res.data.data;
                        if (res.data.code===0){
                            this.ruleForm.realName=datas.realName===null?"未填写":datas.realName;
                            this.ruleForm.address=datas.address===null?"暂无":datas.address;
                            this.ruleForm.nick=datas.nickname;
                            this.ruleForm.check= datas.isCheck===1?'是':'否';
                            this.ruleForm.name=datas.name;
                            this.ruleForm.age= datas.age;
                            if(datas.sex===0){
                                this.ruleForm.sex="未知";
                            }else if(datas.sex===1){
                                this.ruleForm.sex="女";
                            }else{
                                this.ruleForm.sex="男";
                            }

                        }else{
                            let msg=res.data.message;
                            this.$message.error( msg);
                        }
                    })
                    .catch(error=>{
                        console.error(error);
                    });
            },
            submitForm() {
                this.isInput.readOnly=false;
                this.$message({
                    type: 'info',
                    message: '进入修改页面'
                });
            },
            submitSure() {
                const nick=document.getElementById('nick').value;
                const realName=document.getElementById('realName').value;
                const sex=document.getElementById('sex').value;
                const age=document.getElementById('age').value;
                const defaultAddress="0"; //暂时不启用默认地址
                let sex2=0;
                if(sex==='保密'){
                    sex2=0;
                }else if(sex==='女'){
                    sex2=1;
                }else{
                    sex2=2;
                }
                axios.post('/auth/update-info',{
                    nickName:nick,
                    realName:realName,
                    age:age,
                    sex:sex2,
                    defaultAddress:defaultAddress
                })
                    .then(res => {
                        console.log(res.data);
                        if (res.data.code===0){
                            this.$message.success('修改成功！');
                           this.returnRead();
                           this.reflushIt();
                        }else{
                            let msg=res.data.message;
                            this.$message.error(msg);
                        }
                    })
                    .catch(error=>{
                        console.error(error);
                    });
                this.reflushIt();
                this.$router.go(0);
    },

            CheckOne(){
                this.dialogFormVisible = false;
                axios.post('/auth/identify',{
                    realName:this.Checkform.realname,
                    id_number:this.Checkform.IdNumber
                })
                    .then(res => {
                        console.log(res.data);
                        if (res.data.code===0){
                            this.$message.success('实名认证成功！');
                            this.reflushIt();
                            this.ruleForm.check='是'
                            this.$router.go(0)
                        }else{
                            let msg=res.data.message;
                            this.$message.error(msg);
                        }
                    })
                    .catch(error=>{
                        console.error(error);
                    });
                this.reflushIt();
            },
            returnRead(){
                this.isInput.readOnly=true;
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    }
</script>

<style lang="less" scoped>

    .box-card {
        width: 580px;
        margin: 30px 0 0 250px;
        border: 1px solid #1f808c;
    }
    .inputSize{
        width: 320px;
    }
    .inputSize2{
        width: 100px;
    }
    .userTitle{
        text-align: center;
        color:#112f50;
    }
    .userAvatar{
        text-align: center;
    }
    .dialogForm{
        width: 1080px;
        margin: 20px 0 0 250px;
        border: 1px solid #1f808c;
    }

</style>