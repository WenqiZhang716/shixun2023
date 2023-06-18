<template>
    <div>
        <el-card  shadow="hover">
            <el-form :model="detailForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item prop="manifest">
                    <el-descriptions class="descriptionStyle" title="对应账单" column="2" :model="detailForm.manifest" border>

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
            <el-descriptions class="descriptionStyle" title="对应账单" column="2" :model="detailForm.bill" border>
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
        </el-card>
    </div>
</template>

<script>
    export default {
        name: "billAndManifest",
        data() {
            return {
                dialogFormVisible: false,
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
            }
        }
    }
</script>

<style lang="less" scoped>
    .box-card {
        width: 780px;
        margin: 10px 0 0 250px;
        border: 1px solid #1f808c;

    }

    .userTitle{
        text-align: center;
        color:#112f50;
    }

    .descriptionStyle{
        width: 580px;
        margin: 30px 0 0 0px;
    }

</style>