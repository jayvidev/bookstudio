import React from 'react'

interface LogoProps {
  width?: number
  height?: number
  className?: string
}

export const Logo: React.FC<LogoProps> = ({ width = 512, height = 512, className }) => {
  return (
    <svg
      viewBox="0 0 512 512"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      xmlnsXlink="http://www.w3.org/1999/xlink"
      width={width}
      height={height}
      className={className}
    >
      <rect
        id="r4"
        width="512"
        height="512"
        x="0"
        y="0"
        rx="128"
        fill="#222"
        stroke="#FFFFFF"
        strokeWidth="0"
        strokeOpacity="100%"
        paintOrder="stroke"
      />
      <rect
        width="512"
        height="512"
        x="0"
        y="0"
        fill="url(#r6)"
        rx="128"
        style={{ mixBlendMode: 'overlay' }}
      />
      <clipPath id="clip">
        <use xlinkHref="#r4" />
      </clipPath>
      <defs>
        <linearGradient
          id="r5"
          gradientUnits="userSpaceOnUse"
          gradientTransform="rotate(135)"
          style={{ transformOrigin: 'center center' }}
        >
          <stop stopColor="#222" />
          <stop offset="1" stopColor="#222222" />
        </linearGradient>
        <radialGradient
          id="r6"
          cx="0"
          cy="0"
          r="1"
          gradientUnits="userSpaceOnUse"
          gradientTransform="translate(256) rotate(90) scale(512)"
        >
          <stop stopColor="white" />
          <stop offset="1" stopColor="white" stopOpacity="0" />
        </radialGradient>
      </defs>
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="310"
        height="310"
        viewBox="0 0 24 24"
        fill="none"
        stroke="#e8e8e8"
        strokeWidth="1.325"
        strokeLinecap="round"
        strokeLinejoin="round"
        x="101"
        y="101"
      >
        <rect width="8" height="18" x="3" y="3" rx="1" />
        <path d="M7 3v18" />
        <path d="M20.4 18.9c.2.5-.1 1.1-.6 1.3l-1.9.7c-.5.2-1.1-.1-1.3-.6L11.1 5.1c-.2-.5.1-1.1.6-1.3l1.9-.7c.5-.2 1.1.1 1.3.6Z" />
      </svg>
    </svg>
  )
}
