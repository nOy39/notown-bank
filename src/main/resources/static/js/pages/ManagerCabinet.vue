<template>
<div>
    <h3>Manager Cabinet</h3>
    <button type="button" class="btn btn-outline-info" @click="logOut">Log out</button>
</div>
</template>

<script>
    export default {
        name: "ManagerCabinet",
        methods: {
            logOut() {
                localStorage.clear()
                this.$router.push('/')
            }
        }
    }
</script>

<style scoped>

</style>
