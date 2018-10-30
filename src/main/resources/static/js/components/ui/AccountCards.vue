<template>
    <div>
        <b-card-group deck
                      class="mb-3">

            <b-card v-for="(acc, index) in accounts"
                    :key="index"
                    :bg-variant="typeInfo(acc)"
                    text-variant="white"
                    header="Secondary"
                    class="text-center">
                <!--style="max-width: 30%;">-->
                <p class="card-text"><i :class="currencyInfo(acc)"></i></p>
            </b-card>
        </b-card-group>
    </div>
</template>

<script>
    export default {
        name: "AccountCards",
        data() {
            return {
                accounts: [
                    {
                        blocked: false,
                        createdAt: '2018-10-17T08:31:48.661',
                        currency: {
                            name: 'RUB'
                        },
                        default: false,
                        id: 43,
                        sum: 3601.5,
                        typeAccount: {
                            id: 2,
                            type: 'DEBIT'
                        },
                        uniqCheckId: '8d5e-67316852689a-8d5e-67316852689a',
                        updateBy: 8,
                        updatedAt: '2018-10-24T17:08:57.947'
                    },
                    {
                        blocked: false,
                        createdAt: '2018-10-17T08:31:48.661',
                        currency: {
                            name: 'EUR'
                        },
                        default: false,
                        id: 43,
                        sum: 3601.5,
                        typeAccount: {
                            id: 2,
                            type: 'ACCUM'
                        },
                        uniqCheckId: '2828-4f58-8d5e-8d5e-67316852689a',
                        updateBy: 8,
                        updatedAt: '2018-10-24T17:08:57.947'
                    },
                    {
                        blocked: false,
                        createdAt: '2018-10-17T08:31:48.661',
                        currency: {
                            name: 'USD'
                        },
                        default: false,
                        id: 43,
                        sum: 3601.5,
                        typeAccount: {
                            id: 2,
                            type: 'CREDIT'
                        },
                        uniqCheckId: '619e5792-2828-4f58-8d5e-67316852689a',
                        updateBy: 8,
                        updatedAt: '2018-10-24T17:08:57.947'
                    },
                ],
                currency: {RUB: 'fas fa-ruble-sign fa-3x', USD: 'fas fa-dollar-sign fa-3x', EUR: 'fas fa-euro-sign fa-3x'},
                types: {
                    DEBIT: 'primary', ACCUM: 'secondary', CREDIT: 'success'
                }
            }
        },
        methods: {
            typeInfo(obj) {
                let typeName = obj.typeAccount.type
                if (typeName in this.types) {
                    return this.types[typeName]
                }
            },
            currencyInfo(obj) {
                let currencyName = obj.currency.name
                if (currencyName in this.currency) {
                    return this.currency[currencyName]
                }
            }
        }
    }
</script>

<style scoped>

</style>
