/* eslint-disable */
<template>
    <div>
        <div class="navbar-title">
            <h1>导航栏</h1>
        </div>

            <el-menu
                    default-active="/home"
                    class="el-menu-vertical-demo"
                    background-color="#112f50"
                    text-color="#fff" active-text-color="#ffd04b"
                    router>
                <el-menu-item index="/home">
                    <i class="el-icon-menu"></i>
                    <span slot="title" class="fontSize">首页</span>
                </el-menu-item>
                <el-submenu index="2">
                    <template slot="title" >
                        <i class="el-icon-s-tools"></i>
                        <span class="fontSize">用户模块</span>
                    </template>
                    <el-menu-item-group >
                        <el-menu-item index="/user/user-info">个人信息</el-menu-item>
                        <el-menu-item index="/user/change-pw">修改密码</el-menu-item>
                        <el-menu-item index="/user/bank-account">银行卡管理</el-menu-item>
                    </el-menu-item-group>

                </el-submenu>

                <el-submenu index="3">
                    <template slot="title">
                        <i class="el-icon-menu"></i>
                        <span class="fontSize">货单管理</span>
                    </template>
                    <el-menu-item-group >
                        <el-menu-item index="/manifest/create">货单创建</el-menu-item>
                        <el-menu-item index="/manifest/manage">货单操作</el-menu-item>
                    </el-menu-item-group>

                </el-submenu>

                <el-submenu index="4">
                    <template slot="title">
                        <i class="el-icon-document"></i>
                        <span class="fontSize">账单管理</span>
                    </template>
                    <el-menu-item-group >
                        <el-menu-item index="/bill/manage">账单操作</el-menu-item>
                    </el-menu-item-group>

                </el-submenu>

                <el-submenu index="5">
                    <template slot="title">
                        <i class="el-icon-location"></i>
                        <span class="fontSize">运输管理</span>
                    </template>
                    <el-menu-item-group >
                        <el-menu-item index="/transport/transport-plan">运输查询</el-menu-item>
                    </el-menu-item-group>

                </el-submenu>

            </el-menu>
    </div>
</template>

<script>
    export default {
        name: "IndexMenu"
    }
</script>

<style lang="less" scoped>
    .el-menu{
        margin-top: 20px;
        border-right: 0;
        /deep/.is-active{
            background: cornflowerblue !important ;

        }
    }
    .fontSize{
        font-size: 18px;
    }

    .navbar-title {
        text-align: center;
        margin-top: 10px;
        color: #fff;
    }

</style>