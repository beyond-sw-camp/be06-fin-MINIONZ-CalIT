import { createRouter, createWebHistory } from "vue-router";
import UserTemplate from "@/view/user/UserTemplate.vue";
import LoginPage from "@/view/user/pages/LoginPage.vue";
import SignupPage from "@/view/user/pages/SignupPage.vue";
import PasswordPage from "@/view/user/pages/PasswordPage.vue";
import CompletePage from "@/view/user/pages/CompletePage.vue";

const routes = [
    {
        path: '/',
        name: 'Thumbnail',
        component: () => import('@/view/thumbnail/ThumbnailPage.vue')
    },
    {
        path: '/user',
        name: 'UserTemplate',
        component: UserTemplate,
        children: [
            {
                path: 'login',
                name: 'Login',
                component: LoginPage
            },
            {
                path: 'signup',
                name: 'Signup',
                component: SignupPage
            },
            {
                path: 'password',
                name: 'Password',
                component: PasswordPage
            },
            {
                path: 'password-reset',
                name: 'PasswordReset',
                component: () => import('@/view/user/pages/PasswordResetPage.vue')
            },
            {
                path: 'complete',
                name: 'Complete',
                component: CompletePage
            }
        ]
    },
    {
        path: '/dashboard',
        name: 'Dashboard',
        children: [
            {
                path: 'personal',
                name: 'PersonalDashboard',
                component: () => import('@/view/dashboard/PersonalDashBoard.vue')
            },
            {
                path: 'workspace',
                name: 'WorkspaceDashboard',
                component: () => import('@/view/dashboard/WorkspaceDashBoard.vue')
            }
        ]
    },
    {
        path: '/schedule',
        name: 'WorkspaceSchedule',
        children: [
            {
                path: 'workspace-monthly',
                name: 'WorkspaceMonthly',
                component: () => import('@/view/schedule/monthly/WorkSpaceMonthly.vue')
            },
            {
                path: 'workspace-weekly',
                name: 'WorkspaceWeekly',
                component: () => import('@/view/schedule/weekly/WorkSpaceWeekly.vue')
            }
        ]
    },
    {
        path: '/backlog',
        name: 'WorkspaceBacklog',
        children: [
            {
                path: 'kanban',
                name: 'WorkspaceBacklogKanban',
                component: () => import('@/view/backlog/kanban/WorkSpaceKanban.vue')
            },
            {
                path: 'list',
                name: 'WorkspaceBacklogList',
                component: () => import('@/view/backlog/list/WorkSpaceList.vue')
            },
            {
                path: 'timeline',
                name: 'WorkspaceBacklogTimeline',
                component: () => import('@/view/backlog/timeline/TimeLine.vue')
            }
        ]
    },
    {
        path: '/scrum',
        name: 'WorkspaceScrum',
        children: [
            {
                path: 'create',
                name: 'WorkspaceScrumCreate',
                children: [
                    {
                        path: 'workspace',
                        name: 'WorkspaceScrumCreateWorkspace',
                        component: () => import('@/view/scrum/create/WorkSpaceCreate.vue')
                    },
                    {
                        path: 'sprint',
                        name: 'WorkspaceScrumCreateSprint',
                        component: () => import('@/view/scrum/create/SprintCreate.vue')
                    },
                    {
                        path: 'issue',
                        name: 'WorkspaceScrumCreateIssue',
                        component: () => import('@/view/scrum/create/IssueCreate.vue')
                    },
                    {
                        path: 'label',
                        name: 'WorkspaceScrumCreateLabel',
                        component: () => import('@/view/scrum/list/LabelList.vue')
                    }
                ]
            },
            {
                path: 'sprint',
                name: 'WorkspaceScrumSprintList',
                component: () => import('@/view/scrum/list/SprintList.vue')
            },
            {
                path: 'meeting',
                name: 'BoardMeeting',
                children: [
                    {
                        path: 'list',
                        name: 'BoardMeetingList',
                        component: () => import('@/view/scrum/meeting/MeetingList.vue')
                    },
                    {
                        path: 'detail',
                        name: 'BoardMeetingDetail',
                        component: () => import('@/view/scrum/meeting/MeetingDetail.vue')
                    },
                    {
                        path: 'create',
                        name: 'BoardMeetingCreate',
                        component: () => import('@/view/scrum/meeting/MeetingEdit.vue')
                    }
                ]
            }
        ]
    },
    {
        path: '/board',
        name: 'Board',
        children: [
            {
                path: 'error',
                name: 'Error',
                children: [
                    {
                        path: 'list',
                        name: 'ErrorList',
                        component: () => import('@/view/board/list/ErrorList.vue')
                    },
                    {
                        path: 'detail',
                        name: 'ErrorDetail',
                        component: () => import('@/view/board/detail/ErrorDetail.vue')
                    },
                    {
                        path: 'create',
                        name: 'ErrorCreate',
                        component: () => import('@/view/board/create/ErrorCreate.vue')
                    }
                ]
            },
            {
                path: 'qa',
                name: 'QA',
                children: [
                    {
                        path: 'list',
                        name: 'QAList',
                        component: () => import('@/view/board/list/QAList.vue')
                    },
                    {
                        path: 'detail',
                        name: 'QADetail',
                        component: () => import('@/view/board/detail/QADetail.vue')
                    },
                    {
                        path: 'create',
                        name: 'QACreate',
                        component: () => import('@/view/board/create/QACreate.vue')
                    }
                ]
            }
        ]
    },
    {
        path: '/chat',
        name: 'Chat',
        component: () => import('@/view/chat/ChatRoom.vue')
    }
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
});

export default router;