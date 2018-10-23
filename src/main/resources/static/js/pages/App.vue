<template>
    <div>
        <popup-message v-show="error"></popup-message>
        <loading v-show="loading"></loading>
    <router-view></router-view>
    </div>
</template>

<script>
    import PopupMessage from "../components/ui/PopupMessage.vue";
    import Loading from "../components/ui/Loading.vue";
    export default {
        components: {Loading, PopupMessage},
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
