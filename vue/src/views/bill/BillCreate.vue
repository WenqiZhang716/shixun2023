<template>
    <div>
        <el-card class="box-card" shadow="hover">
        <h1 class="userTitle">生成账单</h1>
        <el-descriptions class="descriptionStyle" column="1" ref="billForm" :model="billForm">
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

        </el-card>
    </div>
</template>

<script>
    export default {
        name: "BillCreate",
        data() {
            return {
                dialogFormVisible: false,
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

                }
            }
        }
    }
</script>

<style lang="less" scoped>
    .box-card {
        width: 380px;
        margin: 10px 0 0 250px;
        border: 1px solid #1f808c;

    }

    .userTitle{
        text-align: center;
        color:#112f50;
    }

    .descriptionStyle{
        width: 380px;
        margin: 30px 0 0 10px;
    }
</style>