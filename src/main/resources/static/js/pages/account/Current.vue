<template>
    <div class="my-3">
        <AccountCards :accounts="acc"/>
    </div>
</template>

<script>
    import AccountCards from "../../components/data/AccountCards.vue";
    export default {
        components: {AccountCards},
        computed: {
            acc() {
                return this.$store.getters.getClientAccounts
            }
        }
    }
</script>

<style scoped>

</style>
