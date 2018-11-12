<template>
    <div>
        <h3>
            Payment
        </h3>
        <payment-selector></payment-selector>
    </div>
</template>

<script>
    import PaymentSelector from "../../components/data/PaymentSelector.vue";
    export default {
        name: "Payment",
        components: {PaymentSelector}
    }
</script>

<style scoped>

</style>
