<template>
    <div>
        <sub-panel :accounts="accounts"/>

        <DockPanel v-if="shortHistory" :history="shortHistory"/>
    </div>
</template>

<script>
    import SubPanel from "../../components/ui/SubPanel.vue";
    import DockPanel from "../../components/ui/DockPanel.vue";
    import {AXIOS} from "../../http-common";

    export default {
        components: {DockPanel, SubPanel},
        data() {
            return {}
        },
        computed: {
            accounts() {
                return this.$store.getters.getClientAccounts
            },
            shortHistory() {
                return this.$store.getters.getShortHistory
            }
        },
        beforeMount() {
            AXIOS({
                method: 'get',
                url: '/logs/last',
                headers: {
                    'Authorization': 'Bearer ' + this.$store.getters.getToken
                }
            })
                .then(response => {
                    this.$store.dispatch('setShortHistory', response.data)
                })
                .catch(e => {
                    this.$store.dispatch('setError', e.message)
                })
        }
    }
</script>

<style scoped>

</style>
