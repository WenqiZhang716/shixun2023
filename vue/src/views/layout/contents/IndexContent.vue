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
        }
    }
</script>

<style scoped>

</style>