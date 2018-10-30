<template>
    <div>
        <DockPanel/>
    </div>
</template>

<script>

    import DockPanel from "../components/ui/DockPanel.vue";
    export default {
        components: {DockPanel},
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
