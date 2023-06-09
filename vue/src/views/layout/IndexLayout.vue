<template>
    <div class="layout">
        <!--左侧导航栏-->
        <div class="menu">
            <Menu></Menu>
        </div>
        <!--右侧内容-->
    <div class="content">
        <Contents></Contents>
    </div>

    </div>
</template>

<script>
    import Menu from "./menu/IndexMenu.vue"
    import Contents from "./contents/IndexContent"
    export default {
        name: "IndexLayout",
        components:{
            Menu,Contents
        }
    }
</script>

<style lang="less" scoped>
    .layout{
        .menu{
            width: 200px;
            background:#112f50;
            position: fixed;
            left:0;
            top:0;
            bottom: 0;
        }

        .content{
            padding-left: 200px;
        }
    }

</style>