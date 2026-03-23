FROM node:20-alpine AS deps
RUN corepack enable && corepack prepare pnpm@9 --activate
WORKDIR /app
COPY package.json pnpm-lock.yaml ./
RUN pnpm install --frozen-lockfile

FROM node:20-alpine AS builder
WORKDIR /app
COPY --from=deps /app/node_modules ./node_modules
COPY . .

ARG NEXT_PUBLIC_API_URL
ARG NEXT_PUBLIC_SITE_URL

ENV NEXT_PUBLIC_API_URL=$NEXT_PUBLIC_API_URL \
    NEXT_PUBLIC_SITE_URL=$NEXT_PUBLIC_SITE_URL \
    NEXT_TELEMETRY_DISABLED=1

RUN ./node_modules/.bin/next build

FROM node:20-alpine AS runner

RUN addgroup -S bookstudio && adduser -S bookstudio -G bookstudio

WORKDIR /app

ENV NODE_ENV=production \
    PORT=3000 \
    HOSTNAME=0.0.0.0 \
    NEXT_TELEMETRY_DISABLED=1

COPY --from=builder --chown=bookstudio:bookstudio /app/public ./public
COPY --from=builder --chown=bookstudio:bookstudio /app/.next/static ./.next/static
COPY --from=builder --chown=bookstudio:bookstudio /app/.next/standalone ./

USER bookstudio

EXPOSE 3000

CMD ["node", "server.js"]
