<template>
    <div>
        <navbar></navbar>
        <popup-message v-show="error"></popup-message>
        <loading v-show="loading"></loading>
    <router-view></router-view>
    </div>
</template>

<script>
    import PopupMessage from "../components/ui/PopupMessage.vue";
    import Loading from "../components/ui/Loading.vue";
    import Navbar from "../components/ui/Navbar.vue";
    export default {
        components: {Navbar, Loading, PopupMessage},
        computed: {
            error() {
                return this.$store.getters.getError
            },
            loading() {
                return this.$store.getters.getLoading
            }
        }
    }
</script>

<style scoped>

</style>
