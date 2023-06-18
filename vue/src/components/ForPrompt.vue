<template>
    <div>
        <input v-model="input1" type="text" placeholder="Input 1">
        <input v-model="input2" type="text" placeholder="Input 2">
    </div>
</template>

<script>
    export default {
        name: "ForPrompt",
        data(){
            return {
                input1: '',
                input2: '',
                rules: {
                    input1: [
                        {   required: true,message: '请输入身份证号', trigger: 'blur' }
                    ],
                    input2: [
                        {   required: true,message: '请输入真实姓名', trigger: 'blur' }
                    ],

                }
            };
        }
    }
</script>

<style scoped>

</style>