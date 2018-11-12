<template>
    <div>
        <h5>Selector</h5>
        <b-card-group deck class="mb-3">
            <b-card border-variant="secondary"
                    :header="item.label"
                    header-bg-variant="info"
                    header-text-variant="white"
                    align="center"
            v-for="(item, index) in items"
            :key="index">
                <p class="card-text"><i :id="key" :class="item.icon"></i></p>
            </b-card>
        </b-card-group>
    </div>
</template>

<script>
    export default {
        name: "PaymentSelector",
        data() {
            return {
                items: [
                    {icon: 'fas fa-money-check-alt fa-3x', label: 'Transfer to bank account', description: 'Transfer money by other account'},
                    {icon: 'far fa-credit-card fa-3x', label: 'Transfer to Card', description: 'Transfer money by card'},
                    {icon: 'fas fa-piggy-bank fa-3x', label: 'To deposit money', description: 'Create payment to deposit account'},
                    {icon: 'fas fa-shopping-cart fa-3x', label: 'Create payment', description: 'Buy something with bonus to our partnership'},
                ]
            }
        }
    }
</script>

<style scoped>

</style>
