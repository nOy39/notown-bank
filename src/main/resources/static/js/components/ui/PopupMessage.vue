<template>
    <div>
        <div class="alert alert-danger" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <h4 class="alert-heading">Mistake!</h4>
            <p></p>
            <hr>
            <p class="mb-0">{{error}}</p>
            <b-alert :show="dismissCountDown"
                     dismissible
                     fade
                     variant="alert"
                     @dismissed="dismissCountDown=0"
                     @dismiss-count-down="countDownChanged">
                {{error}}
            </b-alert>
        </div>
        <div>
            <b-alert :show="dismissCountDown"
                     dismissible
                     fade
                     variant="warning"
                     @dismissed="dismissCountDown=0"
                     @dismiss-count-down="countDownChanged">
                This alert will dismiss after {{dismissCountDown}} seconds...
            </b-alert>
        </div>
    </div>
</template>
<script>
    export default {
        name: "PopupMessage",
        data(){
            return {
                dismissCountDown: 0,
                dismissSecs: 5,
            }
        },
        computed: {
            error() {
                return this.$store.getters.getError
            }
        },
        methods:{
            countDownChanged (dismissCountDown) {
                this.dismissCountDown = dismissCountDown
            },
            showAlert () {
                this.dismissCountDown = this.dismissSecs
            }
        }
    }
</script>

<style scoped>

</style>
