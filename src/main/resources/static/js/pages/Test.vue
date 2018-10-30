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
                accounts: [
                    {
                        uniqCheckId: '2342-1wwerw-1231-njnjsdf',
                        cardNumber: '2212-3233-1212-23233',
                        status: true,
                        type: 'DEBIT',
                        currency: 'RUB',
                        sum: 30223.0
                    },
                    {
                        uniqCheckId: '2342-1wwerw-1231-njnjsdf',
                        cardNumber: '2212-3233-1212-23233',
                        status: true,
                        type: 'DEBIT',
                        currency: 'RUB',
                        sum: 30223.0
                    },
                    {
                        uniqCheckId: '2342-1wwerw-1231-njnjsdf',
                        cardNumber: '2212-3233-1212-23233',
                        status: true,
                        type: 'DEBIT',
                        currency: 'RUB',
                        sum: 30223.0
                    },
                    {
                        uniqCheckId: '2342-1wwerw-1231-njnjsdf',
                        cardNumber: '2212-3233-1212-23233',
                        status: true,
                        type: 'DEBIT',
                        currency: 'RUB',
                        sum: 30223.0
                    }
                ]
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
