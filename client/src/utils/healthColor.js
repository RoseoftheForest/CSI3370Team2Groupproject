function healthColor(maxHealth, currentHealth) {
    return "success" ? maxHealth / currentHealth > 0.5
         : "warning" ? maxHealth / currentHealth > 0.25
         : "danger"
}

export default healthColor;