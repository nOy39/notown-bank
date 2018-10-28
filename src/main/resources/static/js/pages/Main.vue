<template>

    <div>
        <b-button variant="success" @click="currentDate">Current date</b-button>
        <div>
            <button type="button" @click="getTest">Test</button>
            <hr>
            <h2>Example heading <b-badge>New</b-badge></h2>
            <h2>{{test}}</h2>
            <hr>
            <router-link to="/test">test</router-link>
            <router-link to="/test">test</router-link>
            <router-link to="/account">account</router-link>
            <router-link to="/account">account</router-link>
            <hr>
            <span>{{moment("2018-10-21T07:29:01.471").format('LLL')}}</span>
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
                },
            }
        },
        methods: {
            getTest() {
                AXIOS.get('/test')
                    .then(response => {
                        this.$store.dispatch('setTest', response.data)
                    })
            },
            currentDate() {
                console.log(this.moment().format('YYYY-MM-DDTHH:mm:ss'))
                console.log(this.firstdayOfMonth)
            }
        },
        computed: {
            test() {
                return this.$store.getters.getTest
            },
            isAuth() {
                return this.$store.getters.getAuth
            },
            date() {
                return Date.now()
            },
            firstdayOfMonth() {
                let fDate = this.moment().format('YYYY-MM') + '-01T00:00:00'
                return fDate
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
