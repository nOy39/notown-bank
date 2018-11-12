export default {
    state: {
        test: '',
        error: '',
        loading: false,
        responseMessage: '',
    },
    mutations: {
        SET_TEST(state, payload) {
            state.test = payload
        },
        SET_ERROR(state, payload) {
            state.error = payload
        },
        IS_LOADING(state, payload) {
            state.loading = payload
        },
        SET_RESPONSE_MESSAGE(state, payload) {
            state.responseMessage = payload
        }
    },
    getters: {
        getTest(state) {
            return state.test
        },
        getError(state) {
            return state.error
        },
        getLoading(state) {
            return state.loading
        },
        getResponseMessage(state) {
            return state.responseMessage
        }
    },
    actions: {
        setTest({commit}, payload) {
            commit('SET_TEST', payload)
        },
        setError({commit}, payload) {
            commit('SET_ERROR', payload)
        },
        isLoading({commit}, payload) {
            commit('IS_LOADING', payload)
        },
        setResponseMessage({commit}, payload) {
            if (!payload.status) {
                commit('SET_ERROR', payload.message)
            }
        }
    }
}
