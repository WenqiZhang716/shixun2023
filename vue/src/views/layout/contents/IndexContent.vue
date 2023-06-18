<template>
    <div>
        <!--顶部-->
        <HeaderContent></HeaderContent>
        <!--内容-->
        <router-view></router-view>


    </div>
</template>

<script>
    import HeaderContent from "./HeaderContent.vue";
    export default {
        name: "IndexContent",
        components:{
            HeaderContent
        },
        mounted() {
            this.timeLineHeight = document.documentElement.clientHeight - 10;
            window.onresize = () => {
                this.timeLineHeight = document.documentElement.clientHeight - 10;
            };
        },
        data() {
            return {
                timeLineHeight: "",
            }
        }
    }
</script>
<style lang="less" scoped>

    ::-webkit-scrollbar {
        width: 0 !important;height: 0;
        display: none !important;
    }

</style>