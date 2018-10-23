<template>

    <div>
        <div v-if="!isAuth">
            <registration></registration>
            <login></login>
        </div>
        <div v-else="isAuth">
            <button type="button" @click="signOut">SignOut</button>
        </div>
        <div>
            <button type="button" @click="getTest">Test</button>
            <hr>
            <h2>Example heading <b-badge>New</b-badge></h2>
            <h2>{{test}}</h2>
            <hr>
            <router-link to="/test">test</router-link>
            <hr>
        </div>
    </div>
</template>

<script>
    import {AXIOS} from "../http-common";
    import Registration from "components/auth/Registration.vue";
    import Login from "../components/auth/Login.vue";

    export default {
        components: {
            Login,
            Registration
        },
        data() {
            return {
                login: {
                    usernameOrEmail: '',
                    password: ''
                }
            }
        },
        methods: {
            getTest() {
                AXIOS.get('/test')
                    .then(response => {
                        this.$store.dispatch('setTest', response.data)
                    })
            },
            signOut() {
                localStorage.clear()
                this.$router.push('/')
            }
        },
        computed: {
            test() {
                return this.$store.getters.getTest
            },
            isAuth() {
                return this.$store.getters.getAuth
            }
        },
        mounted() {
            if (sessionStorage.getItem('token') != null) {
                this.$store.dispatch('setAuth')
            }
        }
    }
</script>

<style scoped>

</style>
