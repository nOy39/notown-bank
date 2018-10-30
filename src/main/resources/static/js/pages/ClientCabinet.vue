<template>
    <div>
        <sub-panel :accounts="accounts"/>

    </div>
</template>

<script>
    import SubPanel from "../components/ui/SubPanel.vue";
    export default {
        components: {SubPanel},
        data() {
            return {

            }
        },
        computed: {
            accounts() {
                return this.$store.getters.getClientAccounts
            }
        }
    }
</script>

<style scoped>

</style>
