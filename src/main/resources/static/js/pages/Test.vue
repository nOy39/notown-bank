<template>
    <div class="my-3">
        <account-cards/>
    </div>
</template>

<script>

    import DockPanel from "../components/data/DockPanel.vue";
    import AccountCards from "../components/data/AccountCards.vue";
    export default {
        components: {AccountCards, DockPanel},
        data() {
            return {

            }
        },
        computed: {
            items() {
                return this.$store.getters.getItems
            }
        }
    }
</script>

<style scoped>

</style>
