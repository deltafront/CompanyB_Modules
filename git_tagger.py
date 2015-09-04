from datetime import date
from subprocess import call
import sys

__author__ = 'charlie'

perform_push = False


def main(tag_name):
    switch_to_master()
    tag_release(tag_name)
    push_tag(tag_name)


def switch_to_master():
    if perform_push:
        print("Checking out master branch.")
        print(call(['git', 'checkout', 'master']))


def tag_release(tag_name):
    def commit_message():
        message = "Tagging RELEASE %s. Consult release notes for more details." % tag_name
        message += "\n%s" % get_release_message(tag_name)
        return message
    commit_message = commit_message()
    print("Commit message is: %s" % commit_message)
    if perform_push:
        print("Creating tag '%s'." % tag_name)
        print(call(['git', 'tag', '-a', tag_name, '-m', commit_message]))


def push_tag(tag_name):
    formatted_tag = "%s_%s" % (tag_name, get_date())
    print("Formatted tag is '%s'." % formatted_tag)
    if perform_push:
        print("Pushing out tag '%s'." % formatted_tag)
        print(call(['git', 'push', 'origin', formatted_tag]))


def get_release_message(tag):
    lines = open_release_notes()
    rest_of_lines = []
    size = len(lines)
    for i in range(0,size):
        line = lines[i]
        if line_includes_tag(line, tag):
            rest_of_lines = lines[i:]
            break
    message = get_release_message_string(rest_of_lines)
    return message


def line_includes_tag(line, tag):
    tag_replacement = tag.replace("-", ".")
    tag_replacement = tag_replacement.replace("_", ".")
    return line.find(tag_replacement) > -1


def open_release_notes():
    readme = open("RELEASE.md", "r")
    lines = readme.readlines()
    readme.close()
    return lines


def get_release_message_string(lines):
    out = ""
    for line in lines:
        out += "\t%s\n" % strip_git_link(line)
    return out


def strip_git_link(line):
    replaced_line = line
    if replaced_line.find("[card]") > -1:
        replaced_line = "\t%s" % replaced_line.replace("[card]", "")
    if replaced_line.find("https") > -1:
        replaced_line = "\t%s" % replaced_line.replace("(", "").replace(")", "")
    return replaced_line


def get_date():
    def add_zero(value):
        return "0%d" % value if int(value) < 10 else value
    today = date.today()
    year = today.year
    month = add_zero(today.month)
    day = add_zero(today.day)
    return "%s_%s_%s" %(year, month, day)

if __name__ == "__main__":
    main(sys.argv[1])