<template>
    <div>
        <b-row>
            <b-col sm="4" v-for="acc in accounts"
                   :key="acc.accountNumber">
                <b-card :bg-variant="typeInfo(acc)"
                        text-variant="white"
                        :header="acc.accountNumber"
                        class="text-center my-3"
                        @click="openAcc(acc.accountNumber)">
                    <!--style="max-width: 30%;">-->
                    <h3>{{acc.sum + ' '}}<i :class="currencyInfo(acc)"></i></h3>
                </b-card>
            </b-col>
        </b-row>
    </div>
</template>

<script>

    export default {
        name: "AccountCards",
        props:{
            accounts: Array
        },
        data() {
            return {
                currency: {
                    RUB: 'fas fa-ruble-sign',
                    USD: 'fas fa-dollar-sign',
                    EUR: 'fas fa-euro-sign'
                },
                types: {
                    DEBIT: 'primary', ACCUM: 'secondary', CREDIT: 'success'
                }
            }
        },
        methods: {
            typeInfo(obj) {
                let typeName = obj.type.typeName
                if (typeName in this.types) {
                    return this.types[typeName]
                }
            },
            currencyInfo(obj) {
                let currencyName = obj.currency.currencyName
                if (currencyName in this.currency) {
                    return this.currency[currencyName]
                }
            },
            openAcc(key) {
                this.$router.push('/account/' + key)
            },
            test(key) {
                console.log(key)
            }
        }
    }
</script>

<style scoped>

</style>
