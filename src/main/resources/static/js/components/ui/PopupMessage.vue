<template>
        <div class="alert alert-danger" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <h4 class="alert-heading">Mistake!</h4>
            <p></p>
            <hr>
            <p class="mb-0">{{error}}</p>
        </div>
</template>
<script>
    export default {
        name: "PopupMessage",
        computed: {
            error() {
                return this.$store.getters.getError
            }
        }
    }
</script>

<style scoped>

</style>
