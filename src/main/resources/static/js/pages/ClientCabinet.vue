<template>
    <div>
        <h2>Client Account</h2>
        <button type="button" class="btn btn-info" @click="logOut">LogOut</button>
        <hr>
        <router-link to="/">main</router-link>
    </div>
</template>

<script>
    import {AXIOS} from "../http-common";

    export default {
        name: "PersonCabinet",
        data() {
            return {
                userInfo: ''
            }
        },
        methods: {
            logOut() {
                sessionStorage.clear()
                this.$store.dispatch('clearAuth')
                    .then(() =>{
                        this.$router.push('/')
                    })
                    .catch(e => {
                        console.log(e)
                    })
            },
            meInfo() {
                AXIOS({
                    method: 'get',
                    url: '/authMe',
                    headers: {
                        'Authorization': sessionStorage.getItem('auth')
                    }
                })
                    .then(response => {
                        this.userInfo = response.data
                    })
                    .catch(e => {
                        console.log(e.message)
                    })
            }
        },
        computed: {
            currentAcc() {
                return this.$store.getters.getAccounts
            },
            date() {
                return this.currentAcc
            }
        }
    }
</script>

<style scoped>

</style>
